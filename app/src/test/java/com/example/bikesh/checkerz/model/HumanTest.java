package com.example.bikesh.checkerz.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HumanTest {

    private Human human;

    @Before
    public void setUp() throws Exception {
        this.human = new Human("Test Human");
    }

    @Test
    public void chooseMove() {
        GameState givenState = new GameState();
        GameState chosenChildState = human.chooseMove(givenState);
        assertNotNull(chosenChildState);
    }
}