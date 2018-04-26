package com.example.bikesh.checkerz.model;

/**
 * Created by dhemard on 3/10/18.
 *
 * A class for managing the actual game. Two players are chosen, one as black
 * and one as red. Black goes first.
 */

public class Game {
    private final IPlayer blackPlayer;
    private final IPlayer redPlayer;
    private int blackMoves;
    private int blackCaptures;
    private int redMoves;
    private int redCaptures;
    private IPlayer winner;
    private GameState currentState;

    public Game(IPlayer blackPlayer, IPlayer redPlayer){
        this.blackPlayer = blackPlayer;
        this.redPlayer = redPlayer;
        this.winner = null;
        this.blackMoves = 0;
        this.redMoves = 0;
        this.blackCaptures = 0;
        this.redCaptures = 0;
        this.currentState = new GameState();
    }
    
    public void advanceTurn(GameState chosenState) {
        if (currentState.getCurrentColor() == PieceColor.BLACK) {
            blackMoves++;
            //blackCaptures += (currentState.getBoard().numberOfRedPieces()
            //        - chosenState.getBoard().numberOfRedPieces());
            blackCaptures = 12 - chosenState.getBoard().numberOfRedPieces();
        } else {
            redMoves++;
            //redCaptures += (currentState.getBoard().numberOfBlackPieces()
            //        - chosenState.getBoard().numberOfBlackPieces());
            redCaptures = 12 - chosenState.getBoard().numberOfBlackPieces();
        }

        // Switch the current state to the chosen state, which makes it the OTHER PLAYER'S TURN
        this.currentState = chosenState;
        // Reset the child state counter so the bot can select again next time
        this.currentState.clearChildCounter();
        // TODO: If there are performance issues may have to remove this state's previous state
        if (chosenState.isOver()) {
            this.winner = determineWinner();
        }
    }

    /**
     * When the current state of the game is over, one of the two players of the
     * game is determined to be the winner.
     * @return
     */
    private IPlayer determineWinner(){
        IPlayer winner = null;
        // If the current color cannot move, the other color has won
        if (!this.currentState.currentColorCanMove()) {
            winner = this.currentState.getCurrentColor() == PieceColor.BLACK ?
                    redPlayer : blackPlayer;
        }
        return winner;
    }

    /**
     * A method to reset the state of the Game to as it was when it started.
     * Note: This keeps the same players. To choose new players, construct a new Game instance
     */
    public void resetGame() {
        this.winner = null;
        this.blackMoves = 0;
        this.redMoves = 0;
        this.blackCaptures = 0;
        this.redCaptures = 0;
        this.currentState = new GameState();
    }

    public IPlayer getBlackPlayer() {
        return blackPlayer;
    }

    public IPlayer getRedPlayer() {
        return redPlayer;
    }

    public int getBlackMoves() {
        return blackMoves;
    }

    public void setBlackMoves(int blackMoves) {
        this.blackMoves = blackMoves;
    }

    public int getBlackCaptures() {
        return blackCaptures;
    }

    public void setBlackCaptures(int blackCaptures) {
        this.blackCaptures = blackCaptures;
    }

    public int getRedMoves() {
        return redMoves;
    }

    public void setRedMoves(int redMoves) {
        this.redMoves = redMoves;
    }

    public int getRedCaptures() {
        return redCaptures;
    }

    public void setRedCaptures(int redCaptures) {
        this.redCaptures = redCaptures;
    }

    public IPlayer getWinner() {
        return winner;
    }

    public GameState getCurrentState() {
        return currentState;
    }
}
