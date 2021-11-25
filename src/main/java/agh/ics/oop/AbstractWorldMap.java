package agh.ics.oop;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected final MapVisualizer visualizer;
    protected final Map<Vector2d, Animal> animals;
    protected final MapBoundary animals_bounds;
    protected Vector2d upper_bound;
    protected Vector2d lower_bound;

    public AbstractWorldMap() {
        this.animals = new LinkedHashMap<>();
        this.visualizer = new MapVisualizer(this);
        this.animals_bounds = new MapBoundary();
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.objectAt(position) != null;
    }

    @Override
    public void place(Animal animal) {
        Vector2d new_position = animal.getPosition();
        if(this.canMoveTo(new_position)){
            this.animals.put(new_position, animal);
            this.animals_bounds.addObjectToTrack(animal);
            animal.addObserver(this);
            animal.addObserver(animals_bounds);
        } else {
            throw new IllegalArgumentException("Can not place at specified position");
        }
    }

    abstract protected void updateBounds();

    @Override
    public String toString(){
        this.updateBounds();
        return this.visualizer.draw(this.lower_bound, this.upper_bound);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        Animal moving_animal = this.animals.get(oldPosition);
        this.animals.remove(oldPosition);
        this.animals.put(newPosition, moving_animal);
    }
}
