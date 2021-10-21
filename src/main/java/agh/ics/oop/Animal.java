package agh.ics.oop;

public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2, 2);
    
    public String toString() {
        return "Direction: " + this.direction.toString() + " Position: " + this.position.toString(); 
    }

    public void move(MoveDirection direction) {
        Vector2d lower_left_bound = new Vector2d(0, 0);
        Vector2d upper_right_bound = new Vector2d(4, 4);
        Vector2d new_position = new Vector2d(this.position.x, this.position.y);
        switch (direction) {
            case LEFT -> this.direction = this.direction.previous();
            case RIGHT -> this.direction = this.direction.next();
            case FORWARD -> new_position = this.position.add(this.direction.toUnitVector());
            case BACKWARD -> new_position = this.position.subtract(this.direction.toUnitVector());
        }
        if ( new_position.precedes(upper_right_bound) && new_position.follows(lower_left_bound)) {
            this.position = new_position;
        }
    }
}
