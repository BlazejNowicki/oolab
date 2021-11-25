package agh.ics.oop;

public class Grass extends AbstractMapObject{
    private Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    public String toString() {
        return "*";
    }
}
