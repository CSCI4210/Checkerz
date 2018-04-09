package com.example.bikesh.checkerz.model;

import com.example.bikesh.checkerz.GameState;

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
    private int redMoves;
    private IPlayer winner;
    private GameState currentState;

    public Game(IPlayer blackPlayer, IPlayer redPlayer){
        this.blackPlayer = blackPlayer;
        this.redPlayer = redPlayer;
        this.winner = null;
        this.blackMoves = 0;
        this.redMoves = 0;
    }

    public void play(){
        this.currentState = new GameState();
        GameState chosenState;
        /* While loop that handles one turn in the game.
         * While the game is not over: The current player will choose a move
         * from one of the child states of the currentState. That player's
         * move counter should then be incremented. Last, check if there is a
         * winner or if the game is a draw.
         */
        while(!currentState.over){
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

    public int getBlackMoves() {
        return blackMoves;
    }

    public void setBlackMoves(int blackMoves) {
        this.blackMoves = blackMoves;
    }

    public int getRedMoves() {
        return redMoves;
    }

    public void setRedMoves(int redMoves) {
        this.redMoves = redMoves;
    }

    public IPlayer getWinner() {
        return winner;
    }
}
