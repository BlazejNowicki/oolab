package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class MapDirectionTest {
    @Test
    void verifyNext() {
        assertEquals(MapDirection.SOUTH, MapDirection.EAST.next());
        assertEquals(MapDirection.WEST, MapDirection.SOUTH.next());
        assertEquals(MapDirection.NORTH, MapDirection.WEST.next());
        assertEquals(MapDirection.EAST, MapDirection.NORTH.next());
    }

    void verifyPrevious() {
        assertEquals(MapDirection.NORTH, MapDirection.EAST.next());
        assertEquals(MapDirection.EAST, MapDirection.SOUTH.next());
        assertEquals(MapDirection.SOUTH, MapDirection.WEST.next());
        assertEquals(MapDirection.WEST, MapDirection.NORTH.next());
    }
}
