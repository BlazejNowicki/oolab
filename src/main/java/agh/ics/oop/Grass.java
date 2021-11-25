package agh.ics.oop;

public class Grass extends AbstractMapObject{

    public Grass(Vector2d position) {
        this.position = position;
    }

    public String toString() {
        return "*";
    }
}
