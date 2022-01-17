package agh.ics.oop;


import java.util.Random;

public class Direction {    // enum nie byłby lepszy?
    private int direction;
    private static final Random rand = new Random();

    public Direction(int direction) {
        direction = direction % 8;
        if (direction < 0) direction += 8;
        this.direction = direction;
    }

    public Direction() {
        this.direction = rand.nextInt(8);
    }

    public Vector2d turn(Genome genes) { // nie czytelniej przekazać inta?
        int turn = genes.getTurn();

        int new_direction = (this.direction + turn) % 8;
        if (turn == 0 || turn == 4) {
            return this.direction_to_vector(new_direction);
        } else {
            this.direction = new_direction;
            return new Vector2d(0, 0);
        }
    }

    private Vector2d direction_to_vector(int direction) { // czemu ta metoda ignoruje this.direction, a przyjmuje inta?
        return switch (direction) {
            case 0 -> new Vector2d(0, 1);   // nowy wektor co wywołanie
            case 1 -> new Vector2d(1, 1);
            case 2 -> new Vector2d(1, 0);
            case 3 -> new Vector2d(1, -1);
            case 4 -> new Vector2d(0, -1);
            case 5 -> new Vector2d(-1, -1);
            case 6 -> new Vector2d(-1, 0);
            case 7 -> new Vector2d(-1, 1);
            default -> new Vector2d(0, 0);  // w tej sytuacji tylko wyjątek
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction1 = (Direction) o;
        return direction == direction1.direction;
    }

    @Override
    public String toString() {
        return Integer.toString(this.direction);
    }

    public String sourcePath() { // lepiej by to przenieść gdzieś do GUI
        return switch (direction) {
            case 0 -> "0.png";
            case 1 -> "1.png";
            case 2 -> "2.png";
            case 3 -> "3.png";
            case 4 -> "4.png";
            case 5 -> "5.png";
            case 6 -> "6.png";
            case 7 -> "7.png";
            default -> "";  // wyjątek
        };
    }
}