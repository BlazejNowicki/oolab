package agh.ics.oop;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    private final MapVisualizer visualizer;
    protected final Map<Vector2d, Animal> animals;
    protected final MapBoundary animal_bounds;
    protected Vector2d upper_bound;
    protected Vector2d lower_bound;

    public AbstractWorldMap() {
        this.animals = new LinkedHashMap<>();
        this.visualizer = new MapVisualizer(this);
        this.animal_bounds = new MapBoundary();
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
            animal.addObserver(this);
        } else {
            throw new IllegalArgumentException("Can not place at specified position");
        }
    }

    abstract protected void updateBounds();

    abstract public boolean canMoveTo(Vector2d position);

    abstract public Object objectAt(Vector2d position);

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

    public Vector2d getLower_bound() {
        this.updateBounds();
        return this.lower_bound;
    }

    public Vector2d getUpper_bound() {
        this.updateBounds();
        return this.upper_bound;
    }
}
