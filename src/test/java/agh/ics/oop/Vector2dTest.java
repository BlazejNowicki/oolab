package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    @Test
    void equalsTest(){
        Vector2d A = new Vector2d(1,233);
        Vector2d B = new Vector2d(4,233);
        Vector2d C = new Vector2d(1,-5);
        Vector2d D = new Vector2d(400123,-5);
        Vector2d E = new Vector2d(400123,-5);
        Vector2d F = new Vector2d(1,233);
        assertEquals(A, F);
        assertEquals(F, A);
        assertEquals(D, E);
        assertEquals(A, A);
        assertEquals(F, F);
        assertNotEquals(D, A);
        assertNotEquals(C, A);
        assertNotEquals(C, E);
        assertNotEquals(A, B);
        assertNotEquals(E, F);
    }

    @Test
    void toStringTest() {
        assertEquals("(1,2)", new Vector2d(1, 2).toString());
        assertEquals("(3,6)", new Vector2d(3, 6).toString());
        assertEquals("(-3,1234)", new Vector2d(-3, 1234).toString());
        assertEquals("(1543231,-3542345)", new Vector2d(1543231, -3542345).toString());
    }

    @Test
    void precedesTest() {
        Vector2d A = new Vector2d(1,233);
        Vector2d B = new Vector2d(4,-233);
        Vector2d C = new Vector2d(1,-555);
        Vector2d D = new Vector2d(3245,56345);
        Vector2d E = new Vector2d(-400123,-5);
        assertTrue(A.precedes(D));
        assertTrue(A.precedes(A));
        assertTrue(D.precedes(D));
        assertTrue(E.precedes(D));
        assertTrue(E.precedes(A));
        assertFalse(D.precedes(C));
        assertFalse(A.precedes(B));
        assertFalse(A.precedes(C));
    }

    @Test
    void followsTest() {
        Vector2d A = new Vector2d(1,233);
        Vector2d B = new Vector2d(4,-233);
        Vector2d C = new Vector2d(1,-555);
        Vector2d D = new Vector2d(3245,56345);
        Vector2d E = new Vector2d(-400123,-5);
        assertFalse(A.follows(D));
        assertTrue(D.follows(A));
        assertTrue(A.follows(A));
        assertTrue(D.follows(D));
        assertFalse(E.follows(D));
        assertFalse(E.follows(A));
        assertTrue(D.follows(C));
        assertFalse(A.follows(B));
        assertFalse(C.follows(A));
    }

    @Test
    void upperRightTest() {
        Vector2d A = new Vector2d(1,233);
        Vector2d B = new Vector2d(4,-233);
        Vector2d C = new Vector2d(1,-555);
        Vector2d D = new Vector2d(3245,56345);
        assertEquals(new Vector2d(4, 233), A.upperRight(B));
        assertEquals(new Vector2d(4, -233), B.upperRight(C));
        assertEquals(new Vector2d(3245, 56345), D.upperRight(A));
        assertEquals(new Vector2d(1, 233), A.upperRight(A));
    }

    @Test
    void lowerLeft() {
        Vector2d A = new Vector2d(1,233);
        Vector2d B = new Vector2d(4,-233);
        Vector2d C = new Vector2d(1,-555);
        Vector2d D = new Vector2d(3245,56345);
        assertEquals(new Vector2d(1, -233), A.lowerLeft(B));
        assertEquals(new Vector2d(1, -555), B.lowerLeft(C));
        assertEquals(new Vector2d(1, 233), D.lowerLeft(A));
        assertEquals(new Vector2d(1, 233), A.lowerLeft(A));
    }

    @Test
    void addTest() {
        Vector2d A = new Vector2d(2, 6);
        Vector2d B = new Vector2d(4567, 687);
        Vector2d C = new Vector2d(-69854, 90800);
        Vector2d D = new Vector2d(-58, 68);
        Vector2d E = new Vector2d(0, 0);
        assertEquals(new Vector2d(4569, 693), A.add(B));
        assertEquals(new Vector2d(4569, 693), B.add(A));
        assertEquals(new Vector2d(-58, 68), D.add(E));
        assertEquals(new Vector2d(-69912, 90868), C.add(D));
        assertNotEquals(new Vector2d(234, 34), A.add(B));
        assertNotEquals(new Vector2d(4569, 4), A.add(B));
    }

    @Test
    void subtractTest() {
        Vector2d A = new Vector2d(2, 6);
        Vector2d B = new Vector2d(4567, 687);
        Vector2d C = new Vector2d(-69854, 90800);
        Vector2d D = new Vector2d(-58, 68);
        Vector2d E = new Vector2d(0, 0);
        assertEquals(new Vector2d(-4565, -681), A.substract(B));
        assertEquals(new Vector2d(4565, 681), B.substract(A));
        assertEquals(new Vector2d(-58, 68), D.substract(E));
        assertEquals(new Vector2d(-69796, 90732), C.substract(D));
        assertNotEquals(new Vector2d(234, 34), A.substract(B));
        assertNotEquals(new Vector2d(4569, 4), A.substract(B));
    }

    @Test
    void oppositeTest() {
        assertEquals(new Vector2d(-4565, -681), new Vector2d(4565, 681).opposite());
        assertEquals(new Vector2d(4565, 681), new Vector2d(-4565, -681).opposite());
        assertEquals(new Vector2d(-58, 68), new Vector2d(58, -68).opposite());
        assertEquals(new Vector2d(-69796, 90732), new Vector2d(69796, -90732).opposite());
        assertEquals(new Vector2d(234, 34), new Vector2d(-234, -34).opposite());
        assertEquals(new Vector2d(4569, 4), new Vector2d(-4569, -4).opposite());
        assertEquals(new Vector2d(0, 4), new Vector2d(0, -4).opposite());
        assertEquals(new Vector2d(0, 0), new Vector2d(0, 0).opposite());
        assertEquals(new Vector2d(9, 0), new Vector2d(-9, 0).opposite());
        assertNotEquals(new Vector2d(-9, 0), new Vector2d(-9, 0).opposite());
        assertNotEquals(new Vector2d(45, 4), new Vector2d(0, -4).opposite());
        assertNotEquals(new Vector2d(34, 5), new Vector2d(56, 6).opposite());
    }
}
