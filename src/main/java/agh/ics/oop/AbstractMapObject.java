package agh.ics.oop;

public abstract class AbstractMapObject {
    protected Vector2d position;

    public Vector2d getPosition() {
        return this.position;
    }

    public abstract String getResourcePath();
}
