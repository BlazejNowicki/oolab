package agh.ics.oop;

import static java.lang.System.out;

public class World {

    private static void run(String[] directions){
        out.println("Start");
        for (String s: directions) {
            switch (s){
                case "f":
                    out.println("Zwierzak idzie do przodu");
                    break;
                case "b":
                    out.println("Zwierzak idzie do tyłu");
                    break;
                case "r":
                    out.println("Zwierzak skręca w prawo");
                    break;
                case "l":
                    out.println("Zwierzak skręca w lewo");
                    break;
            }
        }
        out.println("Stop");
    }
    public static void main(String[] args) {
        String[] dirs  = {"f", "f", "r", "l"};
        run(dirs);
    }
}
