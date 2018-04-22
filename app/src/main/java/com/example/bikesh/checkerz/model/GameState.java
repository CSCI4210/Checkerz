package com.example.bikesh.checkerz.model;

import java.util.Collections;
import java.util.HashSet;

/**
 * Created by dhemard on 3/9/18.
 */

public class GameState {

    private final GameState previous;
    private final GameBoard board;
    private PieceColor currentColor;
    private boolean over;
    private int turn;

    /**
     * Constructor for creating the initial GameState.
     */
    public GameState(){
        this.previous = null;
        this.board = new GameBoard();
        this.over = false;
        /* The turn counter. Increments after BOTH players have had a chance
         * to move */
        this.turn = 0;
        this.currentColor = PieceColor.BLACK;
    }

    /**
     * Constructor for creating a GameState from a previous state.
     * This will never be called outside of this class.
     *
     * @param parent the GameState this state is a descendant of
     * @param nextBoard the GameBoard that represents the board after a move has
     *                  been made
     */
    private GameState(GameState parent, GameBoard nextBoard) {
        this.previous = parent;
        // This current color is the opposite of the parents current color
        this.currentColor
                = parent.currentColor == PieceColor.BLACK ? PieceColor.RED : PieceColor.BLACK;
        /* If the last player to go was black, then the current player of this
         * state will be red. Since red hasn't had a chance to move yet (black
         * always goes first) the turn counter doesn't increment */
        this.turn = parent.currentColor == PieceColor.BLACK ? parent.turn : parent.turn + 1;
        this.over = checkIfGameIsOver();
        this.board = nextBoard;
    }

    /**
     * Returns a collection of all the possible next states that could result
     * from the current player making one move in this state.
     *
     * @return the next legal states
     */
    public Iterable<GameState> next(){
        //TODO: implement this method for the Bot to work
        /* Check the current color of this state to see whose turn it is
         * Iterate over the current color's pieces in this state's board
         *      For each piece, get the available moves
         *      Add all of the positions in that set of available moves to
         *          one larger set of Total available moves.
         * For each of the moves in Total available moves
         *      Create a new state that would result from taking that move
         *      Add that state to an Iterable of all possible next states */
        return Collections.emptyList();
    }

    /**
     * Returns a collection of all the possible next states that could result
     * from the current player making one move for a given piece in this state.
     *
     * @param piece the piece to be moved
     * @return the next legal states in which the given piece has been moved
     */
    public Iterable<GameState> next(Piece piece) {
        //TODO: implement this method for the Bot to work
        /* Get the available moves for the given piece
         * For each of the available moves in that Set
         *      Create a new state that would result from taking that move
         *      Add that state to an Iterable of all possible next states
         *          resulting from moving that piece */
        return Collections.emptyList();
    }

    /**
     * Returns a child state that results from making the provided move in the current state.
     *
     * @param selectedPiece the piece to move
     * @param currentPosition the location of the selectedPiece on the gameboard
     * @param newPosition the desired location for the selectedPiece
     * @return a State that results from making the move
     * @throws IllegalArgumentException if the move is not valid
     */
    public GameState next(Piece selectedPiece, Position currentPosition, Position newPosition) throws IllegalArgumentException {
        GameBoard currentBoard = this.board;
        /* Move Validation */
        //TODO: Uncomment once GameBoard.getAvailableMoves() is implemented
        /*if (this.currentColor != selectedPiece.color)
            throw new IllegalArgumentException("Selected piece's color and the color whose turn it is no not match");
        if (currentBoard.getGrid()[currentPosition.row][currentPosition.column].isEmpty())
            throw new IllegalArgumentException("Cannot move piece from an empty square");
        if (currentBoard.getGrid()[currentPosition.row][currentPosition.column].getPiece().color != this.currentColor)
            throw new IllegalArgumentException("Cannot move a piece that does not match the color whose turn it is");

        HashSet<Position> availableMoves = currentBoard.getAvailableMoves(currentPosition);
        if (!availableMoves.contains(newPosition)) {
            //TODO: maybe handle this error another way
            throw new IllegalArgumentException("Not an available move");
        }
*/
        GameBoard newBoard = currentBoard.movePiece(currentPosition, newPosition);
        GameState childState = new GameState(this, newBoard);
        return childState;
    }

    /**
     * Checks if the game is over based on the current state.
     *
     * @return true if the game is over, false otherwise
     */
    private boolean checkIfGameIsOver(){
        //TODO: implement this method
        return false;
    }

    public GameState getPrevious() {
        return previous;
    }

    public GameBoard getBoard() {
        return board;
    }

    public PieceColor getCurrentColor() {
        return currentColor;
    }

    public boolean isOver() {
        return over;
    }

    public int getTurn() {
        return turn;
    }
}
