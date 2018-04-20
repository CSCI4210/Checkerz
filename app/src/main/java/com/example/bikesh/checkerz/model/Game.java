package com.example.bikesh.checkerz.model;

/**
 * Created by dhemard on 3/10/18.
 *
 * A class for managing the actual game. Two players are chosen, one as black
 * and one as red. Black goes first.
 */

public class Game {
    final IPlayer blackPlayer;
    final IPlayer redPlayer;
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

    // TODO: Change this method. This would be useful if two Bots were playing against each other. We need something more event driven.
    public void play(){
        this.currentState = new GameState();
        GameState chosenState;
        /* While loop that handles one turn in the game.
         * While the game is not over: The current player will choose a move
         * from one of the child states of the currentState. That player's
         * move counter should then be incremented. Last, check if there is a
         * winner or if the game is a draw.
         */
        while(!currentState.isOver()){
            if(currentState.getCurrentColor() == PieceColor.BLACK){
                chosenState = blackPlayer.chooseMove(currentState);
                blackMoves += 1;
            } else {
                chosenState = redPlayer.chooseMove(currentState);
                redMoves += 1;
            }
            this.currentState = chosenState;
        }
        this.winner = determineWinner();
    }

    /**
     * When the current state of the game is over, one of the two players of the
     * game is determined to be the winner.
     * @return
     */
    private IPlayer determineWinner(){
        //TODO: implement this method. Currently returns null
        return null;
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
