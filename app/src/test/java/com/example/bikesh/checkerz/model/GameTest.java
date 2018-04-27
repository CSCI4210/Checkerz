package com.example.bikesh.checkerz.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    @Before
    public void setUp() throws Exception {
        this.game = new Game(new Human("Test1"), new Human("Test2"));
    }

    @Test
    public void advanceTurn() {
        int movesBeforeAdvance = game.getBlackMoves();
        GameState chosenState = new GameState();

        game.advanceTurn(chosenState);
        int movesAfterAdvance = game.getBlackMoves();
        assertEquals(movesBeforeAdvance + 1, movesAfterAdvance);
        assertTrue(game.getCurrentState() == chosenState);
    }

    @Test
    public void resetGame() {
        GameState stateBeforeReset = game.getCurrentState();

        game.resetGame();
        assertNull(game.getWinner());
        assertEquals(0, game.getBlackMoves());
        assertEquals(0, game.getRedMoves());
        assertEquals(0, game.getBlackCaptures());
        assertEquals(0, game.getRedCaptures());
        assertFalse(stateBeforeReset == game.getCurrentState());
    }
}