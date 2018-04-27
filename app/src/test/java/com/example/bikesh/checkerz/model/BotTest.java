package com.example.bikesh.checkerz.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BotTest {

    private Bot bot;

    @Before
    public void setUp() throws Exception {
        this.bot = new Bot();
    }

    @Test
    public void chooseMove() {
        GameState givenState = new GameState();
        GameState chosenChildState = bot.chooseMove(givenState);
        assertNotNull(chosenChildState);
    }
}