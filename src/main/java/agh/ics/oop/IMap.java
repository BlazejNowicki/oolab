package agh.ics.oop;

public interface IMap {
    Vector2d getLowerBound();
    Vector2d getUpperBound();

    IMapElement objectAt(Vector2d position);

    boolean isOccupied(Vector2d position);

    boolean canMoveTo(Vector2d position);

    Vector2d newPosition(Vector2d position);
    boolean isJungle(Vector2d position);

    void moveElements();
    void reproduction();
    void removeDead();
    void spawnPlants();
    void eatPlants();
    void startTracking(Object obj);
    Tracker getTracker();
    boolean containsDominant(Vector2d position);
    MapConfiguration getConfiguration();

    int getDate();
    Statistics getStatistics();
}
