package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    @Test
    void testA() {
        Animal Bob = new Animal();
        String[] moves = {"f", "r", "r", "ks", "r", "b", "r", "f", "f", "f", "f", "r", "r"};
        for (MoveDirection move: OptionsParser.parse(moves)) {
            Bob.move(move);
        }
        assertEquals("Direction: Południe Position: (3,4)", Bob.toString());
    }

    @Test
    void testB() {
        Animal Jeff = new Animal();
        String[] moves = {"forward", "backward", "left", "asdkj", "", "right", "f", "r", "f", "r", "f", "r", "f", "r"};
        for (MoveDirection move: OptionsParser.parse(moves)) {
            Jeff.move(move);
        }
        assertEquals("Direction: Północ Position: (2,2)", Jeff.toString());
    }

    @Test
    void testC() {
        Animal SalemTheDog = new Animal();
        String[] moves = {"eeast", "foreward", "do a flip", "f", "f", "f", "f", "r"};
        for (MoveDirection move: OptionsParser.parse(moves)) {
            SalemTheDog.move(move);
        }
        assertEquals("Direction: Wschód Position: (2,4)", SalemTheDog.toString());
    }
}
