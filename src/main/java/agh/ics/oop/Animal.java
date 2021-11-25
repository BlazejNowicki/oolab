package agh.ics.oop;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentMap;

public class Animal extends AbstractMapObject{
    private MapDirection direction = MapDirection.NORTH;
    private final IWorldMap map;
    private final ArrayList<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(IWorldMap map) {
        this.position = new Vector2d(2, 2);
        this.map = map;
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.position = new Vector2d(initialPosition.x, initialPosition.y);
        this.map = map;
    }

    protected MapDirection getDirection() {
        return this.direction;
    }

    public void move(MoveDirection direction) {
        Vector2d new_position = new Vector2d(this.position.x, this.position.y);
        switch (direction) {
            case LEFT -> this.direction = this.direction.previous();
            case RIGHT -> this.direction = this.direction.next();
            case FORWARD -> new_position = this.position.add(this.direction.toUnitVector());
            case BACKWARD -> new_position = this.position.subtract(this.direction.toUnitVector());
        }
        if(this.map == null || this.map.canMoveTo(new_position)) {
            this.positionChanged(this.position, new_position);
            this.position = new_position;
        }
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public String toString() {
        return switch (this.direction) {
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
        };
    }

    public void addObserver(IPositionChangeObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }

    private void positionChanged(Vector2d old_position, Vector2d new_position){
        for (IPositionChangeObserver observer: this.observers) {
            observer.positionChanged(old_position, new_position);
        }
    }
}
