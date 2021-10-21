package agh.ics.oop;

import static java.lang.System.out;

public class World {
    private static void run(Direction[] directions){
        out.println("Start");
        for (Direction s: directions) {
            switch (s) {
                case FORWARD -> out.println("Zwierzak idzie do przodu");
                case BACKWARD -> out.println("Zwierzak idzie do tyłu");
                case RIGHT -> out.println("Zwierzak skręca w prawo");
                case LEFT -> out.println("Zwierzak skręca w lewo");
            }
        }
        out.println("Stop");
    }

    private static Direction[] translate(String[] dirs) {
        Direction[] dirs_enum = new Direction[dirs.length];
        for (int i=0; i< dirs.length; i++) {
            switch (dirs[i]) {
                case "f" -> dirs_enum[i] = Direction.FORWARD;
                case "b" -> dirs_enum[i] = Direction.BACKWARD;
                case "r" -> dirs_enum[i] = Direction.RIGHT;
                case "l" -> dirs_enum[i] = Direction.LEFT;
            }
        }
        return dirs_enum;
    }

    public static void main(String[] dirs) {
//        String[] dirs  = {"f", "f", "r", "l"};
//        Direction[] dirs_translated = translate(dirs);
//        run(dirs_translated);
//        Vector2d v = new Vector2d(1,2);
//        out.println(v.toString());
//        Vector2d position1 = new Vector2d(1,1);
//        System.out.println(position1);
//        Vector2d position2 = new Vector2d(-2,2);
//        System.out.println(position2);
//        System.out.println(position1.add(position2));
//        out.println(MapDirection.NORTH);
//        out.println(MapDirection.SOUTH);
//        out.println(MapDirection.EAST);
//        out.println(MapDirection.WEST);
//        out.println(position1.lowerLeft(position2));
//        out.println(position1.upperRight(position2));
//        out.println(MapDirection.NORTH.toUnitVector());
//        out.println(new Vector2d(1543231, -3542345));
        String[] str_dirs = {"r", "f", "f", "f"};
        Animal Bob = new Animal();
        out.println(Bob);
        MoveDirection[] translated = OptionsParser.parse(str_dirs);
        for(MoveDirection dir: translated) {
            Bob.move(dir);
        }
        out.println(Bob);
    }
}
