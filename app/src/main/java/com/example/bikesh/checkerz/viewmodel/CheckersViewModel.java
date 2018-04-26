package com.example.bikesh.checkerz.viewmodel;

import android.databinding.ObservableArrayMap;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.example.bikesh.checkerz.model.Bot;
import com.example.bikesh.checkerz.model.Game;
import com.example.bikesh.checkerz.model.GameState;
import com.example.bikesh.checkerz.model.Human;
import com.example.bikesh.checkerz.model.IPlayer;
import com.example.bikesh.checkerz.model.PieceColor;
import com.example.bikesh.checkerz.model.Position;
import com.example.bikesh.checkerz.model.Square;

import java.util.HashSet;

/**
 * A class representing the ViewModel for the app. This class is a wrapper for the actual Model
 * class. It is tied to the View through the use of Data Binding. Events on the View trigger callback
 * methods in this class which update the Model. Updates to the Model should be paired with updates
 * to the Observable instances in this class, which allow the View to refresh when it observes
 * changes in these instances.
 */
public class CheckersViewModel implements IViewModel {

    private Game model;

    // The View updates itself when changes are made to these objects. The View 'observes' them
    public final ObservableInt blackCaptures = new ObservableInt();
    public final ObservableInt redCaptures = new ObservableInt();
    public final ObservableField<IPlayer> winner = new ObservableField<>();
    public final ObservableBoolean blacksTurn = new ObservableBoolean();
    public final ObservableBoolean redsTurn = new ObservableBoolean();
    public final ObservableArrayMap<String, Integer> grid = new ObservableArrayMap<>();
    // Used to highlight the selected piece's available moves
    public final ObservableArrayMap<String, Boolean> availableMoves = new ObservableArrayMap<>();
    public final ObservableArrayMap<String, Boolean> kingTracker = new ObservableArrayMap<>();

    public CheckersViewModel() {
    }


    //Overridden lifecycle methods used in case something needs to be done to the
    // model during these events
    @Override
    public void onCreate() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onDestroy() {
    }


    //Implement actions callable by the view that will update both
    // the Model and the Observables

    public void onNewGameSelected() {
        this.model = new Game(new Human("Bill"), new Human("Ted"));
        //Set the observables with data from the model
        initializeTurnObservable();
        // Initialize the observable grid with the state of the Game's GameBoard
        syncGridAndAvailableMovesObservables();
        updateBlackCapturesObservable(model.getBlackCaptures());
        updateRedCapturesObservable(model.getRedCaptures());

        // TODO: If the black player is a bot, it should take its turn now
        IPlayer currentPlayer = model.getBlackPlayer();
        if (currentPlayer instanceof Bot) {
            model.advanceTurn(currentPlayer.chooseMove(model.getCurrentState()));
            syncGridAndAvailableMovesObservables();
            toggleTurnObservable();
            updateBlackCapturesObservable(model.getBlackCaptures());
        }
    }

    public void onRestartGameSelected() {
        if (this.model != null) {
            // Reset the state of the model
            this.model.resetGame();
            // Re-initialize the observables
            initializeTurnObservable();
            syncGridAndAvailableMovesObservables();
            updateBlackCapturesObservable(model.getBlackCaptures());
            updateRedCapturesObservable(model.getRedCaptures());

            // TODO: If the black player is a bot, it should take its turn now
            IPlayer currentPlayer = model.getBlackPlayer();
            if (currentPlayer instanceof Bot) {
                model.advanceTurn(currentPlayer.chooseMove(model.getCurrentState()));
                syncGridAndAvailableMovesObservables();
                toggleTurnObservable();
                updateBlackCapturesObservable(model.getBlackCaptures());
            }
        }
    }

    /**
     * A method callable by buttons in the view. When a button is clicked, checks if a move
     * is in progress. If it is, move the piece to the clicked cell (after validating). If not,
     * select the piece and display its available moves by highlighting them in the view.
     *
     * Remember that whenever the model is modified, the related ovbservables should also be
     * modified so that they are not in an inconsistent state.
     * @param row the x coordinate of the clicked button
     * @param col the y coordinate of the clicked button
     */
    public void onCellClickedAt(int row, int col) {
        PieceColor currentColor = model.getCurrentState().getCurrentColor();
        IPlayer currentPlayer = currentColor == PieceColor.BLACK ?
                model.getBlackPlayer() : model.getRedPlayer();

        // If it isn't the humans turn, ignore the click (*So human won't mess with AI logic*)
        if (currentPlayer instanceof Human) {
            Human currentHuman = (Human) currentPlayer;

            //Check if a move is in progress
            if (currentHuman.getSelectedSquare() != null) {
                Position clickedPosition = new Position(row, col);
                Square selectedSquare = currentHuman.getSelectedSquare();

                // If the clicked cell is one of the available moves, continue
                if (model.getCurrentState().getBoard().getAvailableMoves(selectedSquare.getPosition()).contains(clickedPosition)) {
                    GameState desiredState = null;

                    //move the piece to the clicked cell in the model (after validating)
                    desiredState = model.getCurrentState().next(
                            selectedSquare.getPiece(),
                            selectedSquare.getPosition(),
                            clickedPosition
                    );
                    //Clear the availableMoves Observable
                    clearAvailableMovesObservable();
                    //Update the observable with the new piece positions

              /*      boolean resultOfGridObservableUpdate = updateGridObservable(
                            selectedSquare.getPosition(),
                            clickedPosition,
                            currentColor);*/

                    syncGridAndAvailableMovesObservables();
                    //Clear the human's selectedSquare in model (maybe do AFTER updateGridObservable())
                    currentHuman.setSelectedSquare(null);
                    //Toggle the turn in the Observable
                    toggleTurnObservable();
                    //Go to the next turn in the model
                    //** NOTE: CurrentState changes here *************************************//
                    model.advanceTurn(currentHuman.chooseMove(desiredState));
                    //Update the winner observable
                    winner.set(model.getWinner());
                    //Update the captures observable
                    if (currentColor == PieceColor.BLACK)
                        updateBlackCapturesObservable(model.getBlackCaptures());
                    else
                        updateRedCapturesObservable(model.getRedCaptures());

                    //TODO: If other player is a bot it should take its turn now
                    PieceColor nextColor = model.getCurrentState().getCurrentColor();
                    IPlayer nextPlayer = nextColor == PieceColor.BLACK ?
                            model.getBlackPlayer() : model.getRedPlayer();
                    if (nextPlayer instanceof Bot) {
                        GameState chosenMove = nextPlayer.chooseMove(model.getCurrentState());
                        model.advanceTurn(chosenMove);
                        syncGridAndAvailableMovesObservables();
                        toggleTurnObservable();
                        if (nextColor == PieceColor.BLACK)
                            updateBlackCapturesObservable(model.getBlackCaptures());
                        else
                            updateRedCapturesObservable(model.getRedCaptures());
                    }
                } else if (selectedSquare.getPosition().equals(clickedPosition)) {
                    // If the player clicks on their selected piece, the piece is unselected and
                    // they are able to select another piece to move
                    clearAvailableMovesObservable();
                    currentHuman.setSelectedSquare(null);
                }
            } else {
                //Check if the cell has a Piece and is correct color
                Square selected = model.getCurrentState().getBoard().getGrid()[row][col];
                if (!selected.isEmpty() &&
                        selected.getPiece().color == model.getCurrentState().getCurrentColor()) {
                    //'select' the piece in the model
                    currentHuman.setSelectedSquare(selected);
                    //then highlight the available moves in the observable
                    updateAvailableMovesObservable(currentHuman);
                }
            }
        }
    }


    // Private methods for manipulating the observables

    private void initializeTurnObservable () {
        if (model.getCurrentState().getCurrentColor().equals(PieceColor.RED)) {
            this.redsTurn.set(true);
            this.blacksTurn.set(false);
        } else {
            this.blacksTurn.set(true);
            this.redsTurn.set(false);
        }
    }

    private void toggleTurnObservable() {
        if (this.model != null) {
            if (this.blacksTurn.get()) {
                this.blacksTurn.set(false);
                this.redsTurn.set(true);
            } else {
                this.redsTurn.set(false);
                this.blacksTurn.set(true);
            }
        }
    }

    private void updateBlackCapturesObservable(int numberOfCaptures) {
        blackCaptures.set(numberOfCaptures);
    }

    private void updateRedCapturesObservable(int numberOfCaptures) {
        redCaptures.set(numberOfCaptures);
    }

    private void syncGridAndAvailableMovesObservables() {
        //TODO: Investigate if this overwrites the stuff already in grid. If not will need to clear both grid and available moves so that restartGameSelected() works
        Square[][] grid = model.getCurrentState().getBoard().getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (!grid[i][j].isEmpty()) {
                    if (grid[i][j].getPiece().color == PieceColor.BLACK){
                        this.grid.put("" + grid[i][j].getPosition().toString(), 1);
                    } else {
                        this.grid.put("" + grid[i][j].getPosition().toString(), 2);
                    }
                    // For the cells with pieces, add their king flag to the kingTracker
                    this.kingTracker.put("" + grid[i][j].getPosition().toString(),
                            grid[i][j].getPiece().isKing());
                } else {
                    this.grid.put("" + grid[i][j].getPosition().toString(), 0);
                    // For cells without pieces, their king flag needs to be cleared
                    this.kingTracker.replace("" + grid[i][j].getPosition().toString(), false);
                }
                // For every iteration add the square to the Observable for Available Moves
                this.availableMoves.put("" + grid[i][j].getPosition().toString(), false);
            }
        }
    }

    /*private boolean updateGridObservable(
            Position pieceMovedFrom, Position pieceMovedTo, PieceColor color) {

        boolean updateSucceeded = false;

        // The color of the moved piece
        int intColor = (color == PieceColor.BLACK ? 1 : 2);
        String fromKey = pieceMovedFrom.toString();
        String toKey = pieceMovedTo.toString();

        // If both updates succeed, return true
        if((grid.replace(fromKey, intColor, 0)) && (grid.replace(toKey, 0, intColor)))
            updateSucceeded = true;

        return  updateSucceeded;
    }*/

    private void clearAvailableMovesObservable() {
        for (int i = 0; i < model.getCurrentState().getBoard().getGrid().length; i++) {
            for (int j = 0; j < model.getCurrentState().getBoard().getGrid()[i].length; j++) {
                availableMoves.replace("" + String.valueOf(i) + String.valueOf(j), false);
            }
        }
    }
    private void updateAvailableMovesObservable(Human currentHuman) {
        Square selectedSquare = currentHuman.getSelectedSquare();
        HashSet<Position> availableMovePositions =
                model.getCurrentState().getBoard().getAvailableMoves(selectedSquare.getPosition());
        for (Position position : availableMovePositions) {
            availableMoves.replace("" + position.toString(), true);
        }
        // Also highlights the selected piece to give better click feedback to the user
        availableMoves.replace("" + selectedSquare.getPosition().toString(), true);
    }

}
