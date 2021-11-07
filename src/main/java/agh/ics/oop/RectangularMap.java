package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap{
    private final List<Animal> animals;
    private final Vector2d lower_left_bound;
    private final Vector2d upper_right_bound;
    private final MapVisualizer visualizer;

    public RectangularMap(int width, int height) {
        this.animals = new ArrayList<>();
        this.lower_left_bound = new Vector2d(0,0);
        this.upper_right_bound = new Vector2d(width-1, height-1);
        this.visualizer = new MapVisualizer(this);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (!(position.precedes(this.upper_right_bound) && position.follows(this.lower_left_bound)))
            return false;
        return !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if( this.canMoveTo(animal.getPosition()) ){
            this.animals.add(animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for(Animal animal: this.animals) {
            if (animal.isAt(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for(Animal animal: animals)
            if(animal.isAt(position))
                return animal;
        return null;
    }

    public String toString() {
        return this.visualizer.draw(lower_left_bound, upper_right_bound);
    }
}
