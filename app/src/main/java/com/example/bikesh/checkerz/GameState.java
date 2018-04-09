package com.example.bikesh.checkerz;

import com.example.bikesh.checkerz.model.PieceColor;

import java.util.Collections;

/**
 * Created by dhemard on 3/9/18.
 */

public class GameState {

    private GameBoard board;
    private PieceColor currentColor;
    public GameState previous;
    public boolean over;
    public int turn;

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
     * @param parent the GameState this state is a descendant of
     * @param nextBoard the GameBoard that represents the board after a move has
     *                  been made
     */
    public GameState(GameState parent, GameBoard nextBoard){
        this.previous = parent;
        this.board = nextBoard;
        /* If the last player to go was black, then the current player of this
         * state will be red. Since red hasn't had a chance to move yet (black
         * always goes first) the turn counter doesn't increment */
        if(parent.currentColor == PieceColor.BLACK) {
            this.currentColor = PieceColor.RED;
            this.turn = parent.turn;
        }
        /* Otherwise, red went last, so it is black's move now. Since it's red's
         * move, one turn has been completed, so increment turn counter */
        else {
            this.currentColor = PieceColor.BLACK;
            this.turn = parent.turn + 1;
        }
    }

    /**
     * A method used by the current GameState for getting a new GameBoard in
     * which one of a player's pieces has moved.
     */
    private GameBoard getNewBoard(GameBoard currentBoard){
        //TODO: implement this method. Currently returns null
        GameBoard newBoard = null;
        return newBoard;
    }

    /**
     * Returns an Iterator of every possible child of this state that results
     * from the current player moving one of his pieces on the board. Will be
     * used by the Bot when choosing a move.
     *
     * @return A collection of all the possible next states that could result
     * from the current player moving one of his pieces.
     */
    public Iterable<GameState> next(){
        //TODO: implement this method
        return Collections.emptyList();
    }

    private void checkIfGameIsOver(){
        /* Checks if the game is over based on the state. Changes the instance
         * variable to true if it is over */
        //TODO: implement this method
    }

    public GameBoard getBoard() {
        return board;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }

    public PieceColor getCurrentColor() {
        return currentColor;
    }
}
