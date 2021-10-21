package agh.ics.oop;

public class OptionsParser {
    static MoveDirection[] parse(String[] moves) {
        MoveDirection[] translated = new MoveDirection[moves.length];
        int t = 0;
        for (String move : moves) {
            switch (move) {
                case "f", "forward" -> translated[t] = MoveDirection.FORWARD;
                case "b", "backward" -> translated[t] = MoveDirection.BACKWARD;
                case "l", "left" -> translated[t] = MoveDirection.LEFT;
                case "r", "right" -> translated[t] = MoveDirection.RIGHT;
                default -> t--; // don't increment t
            }
            t++;
        }
        MoveDirection[] reduced = new MoveDirection[t];
        System.arraycopy(translated, 0, reduced, 0, t);
        return reduced;
    }
}
