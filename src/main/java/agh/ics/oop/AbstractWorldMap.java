package agh.ics.oop;

import java.util.ArrayList;

public abstract class AbstractWorldMap implements IWorldMap{
    protected final ArrayList<Animal> animals;
    protected final MapVisualizer visualizer;
    protected Vector2d upper_bound;
    protected Vector2d lower_bound;

    public AbstractWorldMap() {
        this.animals = new ArrayList<>();
        this.visualizer = new MapVisualizer(this);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.objectAt(position) != null;
    }

    @Override
    public boolean place(Animal animal) {
        Vector2d new_position = animal.getPosition();
        if(this.canMoveTo(new_position)){
            this.animals.add(animal);
            return true;
        }
        return false;
    }

    abstract protected void updateBounds();

    @Override
    public String toString(){
        this.updateBounds();
        return this.visualizer.draw(this.lower_bound, this.upper_bound);
    }
}
