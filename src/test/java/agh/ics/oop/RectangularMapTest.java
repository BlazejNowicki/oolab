package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    @Test
    void A(){
        String[] moves = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] directions = new OptionsParser().parse(moves);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        String expected_output = """
                 y\\x  0 1 2 3 4 5 6 7 8 9
                  5: ---------------------
                  4: | | | |N| | | | | | |
                  3: | | | | | | | | | | |
                  2: | | | | | | | | | | |
                  1: | | | | | | | | | | |
                  0: | | |S| | | | | | | |
                 -1: ---------------------
                """;
        assertEquals(expected_output, map.toString());
    }

    @Test
    void B(){
        String[] moves = {"f","r","r","l","r","f"};
        MoveDirection[] directions = new OptionsParser().parse(moves);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2)};
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        String expected_output = """
                 y\\x  0 1 2 3 4 5 6 7 8 9
                  5: ---------------------
                  4: | | | | | | | | | | |
                  3: | | | | | | | | | | |
                  2: | | |S| | | | | | | |
                  1: | | | | | | | | | | |
                  0: | | | | | | | | | | |
                 -1: ---------------------
                """;
        assertEquals(expected_output, map.toString());
    }


}
