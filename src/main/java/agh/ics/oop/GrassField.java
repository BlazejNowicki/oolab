package agh.ics.oop;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

public class GrassField implements IWorldMap{
    private final ArrayList<Grass> tufts;
    private final ArrayList<Animal> animals;
    private final MapVisualizer visualizer;
    private Vector2d upper_bound;
    private Vector2d lower_bound;

    public GrassField(int num_of_tufts) {
        this.tufts = new ArrayList<>(num_of_tufts);
        this.animals = new ArrayList<>();
        this.visualizer = new MapVisualizer(this);
        Random rand = new Random();
        rand.setSeed(42);
        int sqrt_bound = (int)Math.sqrt(10*num_of_tufts);

        for (int i =0; i<num_of_tufts; i++){
            Vector2d new_vector;
            do {
                int new_x_cord = rand.nextInt(sqrt_bound);
                int new_y_cord = rand.nextInt(sqrt_bound);
                new_vector = new Vector2d(new_x_cord, new_y_cord);
            } while (this.objectAt(new_vector) != null);
            this.tufts.add(new Grass(new_vector));
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        Object obj = this.objectAt(position);
        return obj == null || obj instanceof Grass;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return !(this.objectAt(position) == null);
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal: this.animals) {
            if(animal.isAt(position))
                return animal;
        }
        for (Grass grass: this.tufts) {
            if (grass.getPosition().equals(position)) {
                return grass;
            }
        }
        return null;
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

    private void updateBounds(Vector2d new_position) {
        this.upper_bound = new Vector2d(Integer.max(new_position.x, this.upper_bound.x),
                Integer.max(new_position.y, this.upper_bound.y));
        this.lower_bound = new Vector2d(Integer.min(new_position.x, this.lower_bound.x),
                Integer.min(new_position.y, this.lower_bound.y));
    }

    public String toString(){
        this.upper_bound = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.lower_bound = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (Animal animal: this.animals)
            this.updateBounds(animal.getPosition());
        for (Grass grass: this.tufts)
            this.updateBounds(grass.getPosition());
        return this.visualizer.draw(this.lower_bound, this.upper_bound);
    }
}
