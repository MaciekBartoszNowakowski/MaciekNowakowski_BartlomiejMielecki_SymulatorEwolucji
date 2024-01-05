package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    @Test
    public void equalsTest() {
        Vector2d first = new Vector2d(1, 2);
        Vector2d firstCopy = first;
        Vector2d second = new Vector2d(1, 2);
        Vector2d third = new Vector2d(2, 1);
        assertEquals(first, firstCopy);
        assertEquals(first, second);
        assertNotEquals(first, third);

    }

    @Test
    public void toStringTest() {
        Vector2d vector = new Vector2d(1, 2);
        String text = vector.toString();
        assertEquals("(1,2)",text);
        assertNotEquals("(2, 1)", text);

    }

    @Test
    public void precedesTest() {
        Vector2d testedvector = new Vector2d(1, 1);
        Vector2d smaller = new Vector2d(0, 0);
        Vector2d bigger = new Vector2d(2, 2);
        Vector2d equal = new Vector2d(1, 1);
        Vector2d between = new Vector2d(0, 2);

        assertFalse(testedvector.precedes(smaller));
        assertTrue(testedvector.precedes(equal));
        assertTrue(testedvector.precedes(bigger));
        assertFalse(testedvector.precedes(between));

    }

    @Test
    public void follows() {
        Vector2d testedvector = new Vector2d(1, 1);
        Vector2d smaller = new Vector2d(0, 0);
        Vector2d bigger = new Vector2d(2, 2);
        Vector2d equal = new Vector2d(1, 1);
        Vector2d between = new Vector2d(0, 2);
        assertTrue(testedvector.follows(smaller));
        assertTrue(testedvector.follows(equal));
        assertFalse(testedvector.follows(bigger));
        assertFalse(testedvector.follows(between));

    }

    @Test
    public void upperRightTest() {
        Vector2d first = new Vector2d(-3,2);
        Vector2d second = new Vector2d(2, 3);
        Vector2d third = new Vector2d(-6,6);
        Vector2d fourth = new Vector2d(-1,2);

        assertEquals(new Vector2d(2,3),first.upperRight(second));
        assertEquals(new Vector2d(-3,6),first.upperRight(third));
        assertEquals(new Vector2d(-1,2),first.upperRight(fourth));


    }

    @Test
    public void lowerLeftTest() {
        Vector2d first = new Vector2d(-3,2);
        Vector2d second = new Vector2d(2, 3);
        Vector2d third = new Vector2d(-6,6);
        Vector2d fourth = new Vector2d(-1,2);

        assertEquals(new Vector2d(-3,2),first.lowerLeft(second));
        assertEquals(new Vector2d(-6,2),first.lowerLeft(third));
        assertEquals(new Vector2d(-3,2),first.lowerLeft(fourth));

    }

    @Test
    public void addTest() {
        Vector2d first = new Vector2d(-1,2);
        Vector2d second = new Vector2d(2, 1);
        Vector2d third = new Vector2d(-3,-4);
        Vector2d fourth = new Vector2d(0,0);

        assertEquals(new Vector2d(1,3),first.add(second));
        assertEquals(new Vector2d(-4,-2),first.add(third));
        assertEquals(new Vector2d(-1,2),first.add(fourth));

    }

    @Test
    public void substractTest() {
        Vector2d first = new Vector2d(-1,2);
        Vector2d second = new Vector2d(2, 1);
        Vector2d third = new Vector2d(-3,-4);
        Vector2d fourth = new Vector2d(0,0);

        assertEquals(new Vector2d(-3,1),first.substract(second));
        assertEquals(new Vector2d(2,6),first.substract(third));
        assertEquals(new Vector2d(-1,2),first.substract(fourth));
    }

    @Test
    public void oppositeTest() {
        Vector2d first = new Vector2d(1,2);
        Vector2d second = new Vector2d(-1, 2);
        Vector2d third = new Vector2d(-1,-2);
        Vector2d fourth = new Vector2d(0,0);

        assertEquals(new Vector2d(-1,-2),first.opposite());
        assertEquals(new Vector2d(1,-2),second.opposite());
        assertEquals(new Vector2d(1,2),third.opposite());
        assertEquals(new Vector2d(0,0),fourth.opposite());

    }
}
