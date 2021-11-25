package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapBoundaryTest {
    @Test
    void testA(){
        try {
            MapBoundary boundary = new MapBoundary();
            IWorldMap map = new GrassField(10);
            boundary.addObjectToTrack(new Animal(map, new Vector2d(-1, 2)));
            boundary.addObjectToTrack(new Animal(map, new Vector2d(0, 0)));
            assertEquals(new Vector2d(-1, 0), boundary.getLowerBound());
            assertEquals(new Vector2d(0, 2), boundary.getUpperBound());
        } catch (IllegalArgumentException e){
            System.out.print(e);
        }
    }

    @Test
    void testB(){
        try {
            MapBoundary boundary = new MapBoundary();
            IWorldMap map = new GrassField(10);
            boundary.addObjectToTrack(new Animal(map, new Vector2d(-1, 2)));
            boundary.addObjectToTrack(new Animal(map, new Vector2d(0, 0)));
            assertEquals(new Vector2d(-1, 0), boundary.getLowerBound());
            assertEquals(new Vector2d(0, 2), boundary.getUpperBound());
        } catch (IllegalArgumentException e){
            System.out.print(e);
        }
    }

}
