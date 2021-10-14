package agh.ics.oop;

import static java.lang.System.out;

public class World {
    private static void run(Direction[] directions){
        out.println("Start");
        for (Direction s: directions) {
            switch (s){
                case FORWARD:
                    out.println("Zwierzak idzie do przodu");
                    break;
                case BACKWARD:
                    out.println("Zwierzak idzie do tyłu");
                    break;
                case RIGHT:
                    out.println("Zwierzak skręca w prawo");
                    break;
                case LEFT:
                    out.println("Zwierzak skręca w lewo");
                    break;
            }
        }
        out.println("Stop");
    }

    private static Direction[] translate(String[] dirs) {
        Direction[] dirs_enum = new Direction[dirs.length];
        for (int i=0; i< dirs.length; i++) {
            switch (dirs[i]) {
                case "f":
                    dirs_enum[i] = Direction.FORWARD;
                    break;
                case "b":
                    dirs_enum[i] = Direction.BACKWARD;
                    break;
                case "r":
                    dirs_enum[i] = Direction.RIGHT;
                    break;
                case "l":
                    dirs_enum[i] = Direction.LEFT;
                    break;
            }
        }
        return dirs_enum;
    }

    public static void main(String[] args) {
        String[] dirs  = {"f", "f", "r", "l"};
        Direction[] dirs_translated = translate(dirs);
        run(dirs_translated);
    }
}
