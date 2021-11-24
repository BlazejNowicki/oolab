package agh.ics.oop;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.lang.Math;

public class GrassField extends AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    private final Map<Vector2d, Grass> tufts;

    public GrassField(int num_of_tufts) {
        this.tufts = new LinkedHashMap<>();
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
            this.tufts.put(new_vector, new Grass(new_vector));
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        Object obj = this.objectAt(position);
        return obj == null || obj instanceof Grass;
    }

    @Override
    public Object objectAt(Vector2d position) {
        Animal candidate = this.animals.get(position);
        if (candidate != null){
            return candidate;
        } else {
            return this.tufts.get(position);
        }
    }

    private void checkOne(Vector2d new_position){
        this.upper_bound = new Vector2d(Integer.max(new_position.x, this.upper_bound.x),
                Integer.max(new_position.y, this.upper_bound.y));
        this.lower_bound = new Vector2d(Integer.min(new_position.x, this.lower_bound.x),
                Integer.min(new_position.y, this.lower_bound.y));
    }

    @Override
    protected void updateBounds() {
        this.upper_bound = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.lower_bound = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (Animal animal: this.animals.values())
            this.checkOne(animal.getPosition());
        for (Grass grass: this.tufts.values())
            this.checkOne(grass.getPosition());
    }


}
