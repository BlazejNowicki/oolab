package agh.ics.oop;

public class OptionsParser {
    public static MoveDirection[] parse(String[] moves) {
        MoveDirection[] translated = new MoveDirection[moves.length];
        int t = 0;
        for (String move : moves) {
            switch (move) {
                case "f", "forward" -> translated[t] = MoveDirection.FORWARD;
                case "b", "backward" -> translated[t] = MoveDirection.BACKWARD;
                case "l", "left" -> translated[t] = MoveDirection.LEFT;
                case "r", "right" -> translated[t] = MoveDirection.RIGHT;
                default -> throw new IllegalArgumentException(move + " is not a legal move exceptions");
            }
            t++;
        }
        return translated;
    }
}
