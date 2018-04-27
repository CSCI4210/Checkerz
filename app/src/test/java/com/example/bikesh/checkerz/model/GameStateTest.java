package com.example.bikesh.checkerz.model;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;


public class GameStateTest {

    // Test fixture
    private GameState currentState;

    @Before
    public void setUp() throws Exception {
        this.currentState = new GameState();
    }


    @Test
    public void incrementChildCounter() {
        int childStatesBeforeIncrement = currentState.getChildStates();
        currentState.incrementChildCounter();
        int childStatesAfterIncrement = currentState.getChildStates();
        assertEquals(childStatesBeforeIncrement + 1, childStatesAfterIncrement);
    }

    @Test
    public void clearChildCounter() {
        for (int i = 0; i < 100; i++) {
            currentState.incrementChildCounter();
        }
        assertTrue(currentState.getChildStates() > 1);
        currentState.clearChildCounter();
        assertEquals(0, currentState.getChildStates());
    }

    @Test
    public void searchLimitReached() {
        currentState.clearChildCounter();
        for (int i = 0; i < 10000; i++) {
            currentState.incrementChildCounter();
        }
        assertEquals(10000, currentState.getChildStates());
        assertTrue(currentState.searchLimitReached());
    }
}