package agh.ics.oop;

public interface IMap {
    Vector2d getLowerBound();
    Vector2d getUpperBound();

    IMapElement objectAt(Vector2d position);

    boolean isOccupied(Vector2d position);

    boolean canMoveTo(Vector2d position);

    Vector2d newPosition(Vector2d position);

    void moveElements();
}
