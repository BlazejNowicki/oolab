package agh.ics.oop;

public class World {

    private static void run(String[] directions){
        for (int i=0; i < directions.length; i++) {
            System.out.print(directions[i]);
            if (i+1 < directions.length) {
                System.out.print(",");
            }
            else {
                System.out.print("\n");
            }
        }
//        System.out.println("zwierzak idzie do przodu");
    }
    public static void main(String[] args) {
        System.out.println("system wystartował");
        String[] dirs  = {"A","B","C"};
        run(dirs);
        System.out.println("system zakończył działanie");
    }
}
