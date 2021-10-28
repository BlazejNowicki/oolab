package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    @Test
    void testA() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal A = new Animal(map);
        String[] moves = {"f", "r", "r", "ks", "r", "b", "r", "f", "f", "f", "f", "r", "r"};
        for (MoveDirection move: OptionsParser.parse(moves)) {
            A.move(move);
        }
        assertEquals(MapDirection.SOUTH, A.getDirection());
        assertNotEquals(MapDirection.NORTH, A.getDirection());
        assertTrue(A.isAt(new Vector2d(3,4)));
        assertEquals(new Vector2d(3,4), A.getPosition());
        assertNotEquals(new Vector2d(1,4), A.getPosition());
    }

    @Test
    void testB() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal B = new Animal(map);
        String[] moves = {"forward", "backward", "left", "asdkj", "", "right", "f", "r", "f", "r", "f", "r", "f", "r"};
        for (MoveDirection move: OptionsParser.parse(moves)) {
            B.move(move);
        }
        assertEquals(MapDirection.NORTH, B.getDirection());
        assertNotEquals(MapDirection.SOUTH, B.getDirection());
        assertTrue(B.isAt(new Vector2d(2,2)));
        assertEquals(new Vector2d(2,2), B.getPosition());
        assertNotEquals(new Vector2d(1,4), B.getPosition());
    }

    @Test
    void testC() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal C = new Animal(map);
        String[] moves = {"eeast", "foreward", "do a flip", "f", "f", "f", "f", "r"};
        for (MoveDirection move: OptionsParser.parse(moves)) {
            C.move(move);
        }
        assertEquals(MapDirection.EAST, C.getDirection());
        assertTrue(C.isAt(new Vector2d(2,4)));
        assertNotEquals(MapDirection.SOUTH, C.getDirection());
        assertEquals(new Vector2d(2,4), C.getPosition());
        assertNotEquals(new Vector2d(1,4), C.getPosition());
    }
}
