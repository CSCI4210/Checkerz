package com.example.bikesh.checkerz.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {

    private Position position12;
    private Position position03;
    private Position position30;
    private Position duplicatePosition12;


    @Before
    public void setUp() throws Exception {
        position12 = new Position(1,2);
        position03 = new Position(0,3);
        position30 = new Position(3,0);
        duplicatePosition12 = new Position(1,2);
    }

    @Test
    public void toString_test() {
        String result = position03.toString();
        assertNotNull(result);
        assertEquals("03", result);

        result = position30.toString();
        assertNotNull(result);
        assertEquals("30", result);
    }

    @Test
    public void equals_SamePositionCoordinates() {
        // Same object
        assertTrue(position12.equals(position12));

        // Different objects, same position coordinates
        assertTrue(position12.equals(duplicatePosition12));
        assertTrue(duplicatePosition12.equals(position12));
    }

    @Test
    public void equals_DifferentPositionCoordinates() {
        // Same object
        assertTrue(position03.equals(position03));

        // Different objects, different position coordinates
        assertFalse(position03.equals(position30));
        assertFalse(position30.equals(position03));

        assertFalse(position12.equals(position03));
        assertFalse(position03.equals(position12));
    }

    @Test
    public void hashCode_SamePositionCoordinates() {
        // Different objects, same position coordinates
        int hash1 = position12.hashCode();
        int hash2 = duplicatePosition12.hashCode();

        assertTrue(hash1 == hash2);
    }

    @Test
    public void hashCode_DifferentPositionCoordinates() {
        // Different objects, different position coordinates
        int hash1 = position03.hashCode();
        int hash2 = position30.hashCode();

        assertTrue(hash1 != hash2);
    }
}