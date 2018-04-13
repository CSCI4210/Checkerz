package com.example.bikesh.checkerz.viewmodel;

import android.databinding.ObservableArrayMap;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.example.bikesh.checkerz.model.Game;
import com.example.bikesh.checkerz.model.Human;
import com.example.bikesh.checkerz.model.IPlayer;
import com.example.bikesh.checkerz.model.PieceColor;
import com.example.bikesh.checkerz.model.Square;

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

    public CheckersViewModel() {
        //TODO: move some of this to a method that is called when a 'New Game' option is selected in the View
        Human human1 = new Human("Bill");
        Human human2 = new Human("Ted");
        this.model = new Game(human1, human2);

        //Set the observables with data from the model
        if (model.getCurrentState().getCurrentColor().equals(PieceColor.RED)) {
            this.redsTurn.set(true);
        } else {
            this.blacksTurn.set(true);
        }
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

    //Implement actions callable by the view that will update both
    // the Model and the Observables

    public void onNewGameSelected() {
        this.model = new Game(new Human("John"), new Human("Jane"));
        //Set the observables with data from the model
        if (model.getCurrentState().getCurrentColor().equals(PieceColor.RED)) {
            this.redsTurn.set(true);
        } else {
            this.blacksTurn.set(true);
        }
        // Initialize the observable grid with the state of the Game's GameBoard
    }

    public void onRestartGameSelected() {
        this.model.resetGame();
        // Initialize the observables
    }

    public void onCellClickedAt(int row, int col) {
        //Check if a move is in progress
            //If yes
                //move the piece to the clicked cell in the model (after validating)
                //Then update the observable with the new piece positions
                //Go to the next turn in the model
                //Toggle the turn in the Observable
            //If not
                //Check if the cell has a Piece
                    //If yes
                        //select the piece in the model
                        //then select the piece in the the observable
                    //If not
                        //Ignore the click
    }

    //TODO: change method name
    public void onSomething() {
        //TODO: Fix null pointer exception. Implement start game menu with callback to start a new game
        Square[][] initialGrid = model.getCurrentState().getBoard().grid;
        for (int i = 0; i < initialGrid.length; i++) {
            for (int j = 0; j < initialGrid[i].length; j++) {
                if (!initialGrid[i][j].isEmpty()) {
                    if (initialGrid[i][j].getPiece().color == PieceColor.BLACK){
                        //Print Debugging
                        System.out.println(initialGrid[i][j].getPosition().toString());
                        this.grid.put("" + initialGrid[i][j].getPosition().toString(), 1);
                    } else {
                        //Print Debugging
                        System.out.println(initialGrid[i][j].getPosition().toString());
                        this.grid.put("" + initialGrid[i][j].getPosition().toString(), 2);
                    }
                } else {
                    //Print Debugging
                    System.out.println(initialGrid[i][j].getPosition().toString());
                    this.grid.put("" + initialGrid[i][j].getPosition().toString(), 0);
                }
            }
        }
    }
}
