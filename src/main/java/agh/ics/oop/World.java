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

    public static void main(String[] args) {

        String[] moves = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] directions = new OptionsParser().parse(moves);
//        IWorldMap map = new RectangularMap(15, 15);
        IWorldMap map = new GrassField(20);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        out.print(map);
        engine.run();
        out.print(map);

        // -----------------------------------------------------
//        IWorldMap map = new GrassField(10);
//        out.println("----------");
//        out.print(map);
//        Animal A = new Animal(map, new Vector2d(4,3));
//        Animal B = new Animal(map, new Vector2d(2,2));
//        map.place(A);
//        map.place(B);
//        out.print(map);
//        B.move(MoveDirection.FORWARD);
//        B.move(MoveDirection.RIGHT);
//        out.print(map);
//        out.println("----------");
    }
}
