package agh.ics.oop;

import static java.lang.System.out;

import agh.ics.oop.gui.App;
import javafx.application.Application;

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
        Application.launch(App.class, args);
    }
}
