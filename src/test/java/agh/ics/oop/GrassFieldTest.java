package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {
    @Test
    void testA(){
        String expected_output = """
                 y\\x  0 1 2 3 4 5 6 7 8 910111213
                 13: -----------------------------
                 12: |*| | | | | | | | | | |*| | |
                 11: | | | |*| | | | | | | | |*| |
                 10: |*|*| | | | |*| |*| | | | | |
                  9: | | | | | | | |*|*| | | | | |
                  8: | | | | | | | | | | | | | | |
                  7: | | | |N| | | | | | | | | | |
                  6: | | | | | | | |*| | | | |*| |
                  5: | | | | | | | | |*| | | |*|*|
                  4: | | | | | | | | | | |*| | | |
                  3: | | |*| | | | | | | | | | |*|
                  2: | | | | | | | | | | | | | | |
                  1: | | | | | | | | | | | | |*| |
                  0: |*| | | | | | | | | | | | | |
                 -1: | | |S| | | | | | | | | | | |
                 -2: -----------------------------
                """;

        String[] moves = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] directions = new OptionsParser().parse(moves);
//        IWorldMap map = new RectangularMap(15, 15);
        IWorldMap map = new GrassField(20);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        assertEquals(expected_output, map.toString());
    }

    @Test
    void testB() {
        String expected_output = """
                 y\\x  0 1 2 3 4 5 6 7 8 9
                 10: ---------------------
                  9: | | | |*| | | | | | |
                  8: | | | | | |*| | | | |
                  7: | | | | | | | | | | |
                  6: | | | | | | | | | | |
                  5: |*| | | | | | | | | |
                  4: | | | | | | | | |*| |
                  3: |*| |N| | | |*| | |*|
                  2: | | |E| | | |*| | | |
                  1: | | | | | | | | | | |
                  0: | | | | | | |*| | | |
                 -1: ---------------------
                """;
        IWorldMap map = new GrassField(10);
        Animal A = new Animal(map, new Vector2d(2,3));
        Animal B = new Animal(map, new Vector2d(2,2));
        map.place(A);
        map.place(B);
        B.move(MoveDirection.FORWARD);
        B.move(MoveDirection.RIGHT);
        assertEquals(expected_output, map.toString());
    }
}
