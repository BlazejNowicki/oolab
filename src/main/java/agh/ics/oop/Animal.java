package agh.ics.oop;

public class Animal {
    private MapDirection direction;
    private Vector2d position;
    private IWorldMap map;

    public Animal() {   // tego konstruktora nie powinno być
        this.direction = MapDirection.NORTH;
        this.position = new Vector2d(2, 2);
        // what to initiate this.map with?
    }

    public Animal(IWorldMap map) {
        this.direction = MapDirection.NORTH;
        this.position = new Vector2d(2, 2);
        this.map = map;
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.direction = MapDirection.NORTH;
        this.position = new Vector2d(initialPosition.x, initialPosition.y);
        this.map = map;
    }

    protected MapDirection getDirection() {
        return this.direction;
    }

    protected Vector2d getPosition() {
        return this.position;
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
            this.position = new_position;
        }
    }

    boolean isAt(Vector2d position) {
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
}
