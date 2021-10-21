package agh.ics.oop;

public class OptionsParser {
    static MoveDirection[] parse(String[] moves) {
        MoveDirection[] translated = new MoveDirection[moves.length];
        for(int i=0; i<moves.length; i++) {
            switch (moves[i]) {
                case "f", "forward" -> translated[i] = MoveDirection.FORWARD;
                case "b", "backward" -> translated[i] = MoveDirection.BACKWARD;
                case "l", "left" -> translated[i] = MoveDirection.LEFT;
                case "r", "right" -> translated[i] = MoveDirection.RIGHT;
            }
        }
        return translated;
    }
}
