package com.example.bikesh.checkerz.model;

import java.util.Collections;
import java.util.HashSet;
import java.lang.Math;

/**
 * Created by dhemard on 3/9/18.
 */

public class GameState {
    private final int SEARCH_LIMIT = 10000;
    /* Used by the bot to determine when to stop searching for a move
     * Should not be modified by anything outside of this class */
    private int childStates;

    private GameState previous;
    private final GameBoard board;
    private PieceColor currentColor;
    private boolean over;
    private int turn;

    /**
     * Constructor for creating the initial GameState.
     */
    public GameState(){
        this.childStates = 0;
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
        this.over = parent.isOver() || nextBoard.getBlackPieces().isEmpty() || nextBoard.getRedPieces().isEmpty();
        this.board = nextBoard;

        //Increment the root ancestor each time a child state is made
        GameState ancestor = this.previous;
        while (ancestor.getPrevious() != null) {
            ancestor = ancestor.getPrevious();
        }
        ancestor.incrementChildCounter();
    }

    /**
     * Returns a collection of all the possible next states that could result
     * from the current player making one move in this state.
     *
     * @return the next legal states
     */
    public Iterable<GameState> next(){
        HashSet<GameState> everyPossibleState = new HashSet<>();
        /* Check the current color of this state to see whose turn it is
         * Iterate over the current color's pieces in this state's board
         * For each piece, get the available moves
         *      For each position in the set of available moves
         *          Make that move on a new GameBoard
         *          Create the child GameState using that board
         *          Add it to the set of all possible next states */
        if (this.currentColor == PieceColor.BLACK) {
            for (Piece bPiece : this.board.getBlackPieces()) {
                Position positionOfPiece = bPiece.getPosition();
                HashSet<Position> availableMoves = this.board.getAvailableMoves(positionOfPiece);
                if (!availableMoves.isEmpty()) {
                    for (Position nextPosition : availableMoves) {
                        GameBoard clone = this.board.cloneBoard();
                        GameBoard nextBoard = clone.movePiece(positionOfPiece, nextPosition);
                        // If a capture occurred
                        if (Math.abs(nextPosition.getX() - positionOfPiece.getX()) == 2) {
                            Position positionOfJumpedPiece = clone.getMid(positionOfPiece, nextPosition);
                            int x = positionOfJumpedPiece.getX();
                            int y = positionOfJumpedPiece.getY();
                            Piece jumpedPiece = nextBoard.getGrid()[x][y].getPiece();
                            jumpedPiece.setCaptured(true);
                            nextBoard.removePiece(jumpedPiece);
                        }
                        GameState resultingState = new GameState(this, nextBoard);
                        everyPossibleState.add(resultingState);
                    }
                }
            }
        } else {
            for (Piece rPiece : this.board.getRedPieces()) {
                Position positionOfPiece = rPiece.getPosition();
                HashSet<Position> availableMoves = this.board.getAvailableMoves(positionOfPiece);
                if (!availableMoves.isEmpty()) {
                    for (Position nextPosition : availableMoves) {
                        GameBoard clone = this.board.cloneBoard();
                        GameBoard nextBoard = clone.movePiece(positionOfPiece, nextPosition);
                        // If a capture occurred
                        if (Math.abs(nextPosition.getX() - positionOfPiece.getX()) == 2) {
                            Position positionOfJumpedPiece = clone.getMid(positionOfPiece, nextPosition);
                            int x = positionOfJumpedPiece.getX();
                            int y = positionOfJumpedPiece.getY();
                            Piece jumpedPiece = nextBoard.getGrid()[x][y].getPiece();
                            jumpedPiece.setCaptured(true);
                            nextBoard.removePiece(jumpedPiece);
                        }
                        GameState resultingState = new GameState(this, nextBoard);
                        everyPossibleState.add(resultingState);
                    }
                }
            }
        }
        // If there are no possible next states, the game's state is OVER
        if (everyPossibleState.isEmpty())
            this.over = true;
        return everyPossibleState;
    }

    /**
     * Returns a collection of all the possible next states that could result
     * from the current player making one move for a piece at the given
     * position in this state.
     *
     * @param consideredPiece the location of the piece to be moved
     * @return the next legal states in which the given piece has been moved
     * OR an empty hashset
     */
    public Iterable<GameState> next(Piece consideredPiece) {
        HashSet<GameState> possibleStates = new HashSet<>();
        Position positionOfPiece = consideredPiece.getPosition();
        HashSet<Position> availableMoves = board.getAvailableMoves(positionOfPiece);
        /* Get the available moves for the given piece
         * For each position in the set of available moves
         *      Make that move on a new GameBoard
         *      Create the child GameState using that board
         *      Add it to the set of possible next states */
        if (!availableMoves.isEmpty()) {
            for (Position nextPosition : availableMoves) {
                GameBoard clone = this.board.cloneBoard();
                GameBoard nextBoard = clone.movePiece(positionOfPiece, nextPosition);
                // If the move was a jump (capture occurred)
                if (Math.abs(nextPosition.getX() - positionOfPiece.getX()) == 2) {
                    Position positionOfJumpedPiece = clone.getMid(positionOfPiece, nextPosition);
                    int x = positionOfJumpedPiece.getX();
                    int y = positionOfJumpedPiece.getY();
                    Piece jumpedPiece = nextBoard.getGrid()[x][y].getPiece();
                    jumpedPiece.setCaptured(true);
                    nextBoard.removePiece(jumpedPiece);
                }
                GameState resultingState = new GameState(this, nextBoard);
                possibleStates.add(resultingState);
            }
        }
        return possibleStates;
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

        if (this.currentColor != selectedPiece.color)
            throw new IllegalArgumentException("Selected piece's color and the color whose turn it is no not match");
        if (currentBoard.getGrid()[currentPosition.row][currentPosition.column].isEmpty())
            throw new IllegalArgumentException("Cannot move piece from an empty square");
        if (currentBoard.getGrid()[currentPosition.row][currentPosition.column].getPiece().color != this.currentColor)
            throw new IllegalArgumentException("Cannot move a piece that does not match the color whose turn it is");

        HashSet<Position> availableMoves = currentBoard.getAvailableMoves(currentPosition);

        if (Math.abs(newPosition.getX()-currentPosition.getX())!=2){
        GameBoard newBoard = currentBoard.movePiece(currentPosition, newPosition);
        GameState childState = new GameState(this, newBoard);
        return childState;}
        else{
            GameBoard newBoard = currentBoard.movePiece(currentPosition, newPosition);
            Position midPosition = newBoard.getMid(currentPosition, newPosition);
            newBoard.getGrid()[midPosition.getX()][midPosition.getY()].getPiece().isCaptured();
            newBoard.removePiece(newBoard.getGrid()[midPosition.getX()][midPosition.getY()].getPiece());
            newBoard.getGrid()[midPosition.getX()][midPosition.getY()].setPiece(null);
            GameState childState = new GameState(this, newBoard);
            return childState;
        }
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

    public void incrementChildCounter() {
        this.childStates += 1;
    }

    public void clearChildCounter() {
        this.childStates = 0;
        this.previous = null;
    }

    public boolean searchLimitReached() {
        GameState ancestor = this;
        while (ancestor.getPrevious() != null) {
            ancestor = ancestor.getPrevious();
        }
        return ancestor.childStates >= ancestor.SEARCH_LIMIT;
    }

}
