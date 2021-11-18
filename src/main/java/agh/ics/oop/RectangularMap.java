package agh.ics.oop;

import java.util.ArrayList;

public class RectangularMap extends AbstractWorldMap implements IWorldMap{
    public RectangularMap(int width, int height) {
        super();
        this.lower_bound = new Vector2d(0,0);
        this.upper_bound = new Vector2d(width-1, height-1);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (!(position.precedes(this.upper_bound) && position.follows(this.lower_bound)))
            return false;
        return !isOccupied(position);
    }

    @Override
    public Object objectAt(Vector2d position) {
        for(Animal animal: animals)
            if(animal.isAt(position))
                return animal;
        return null;
    }

    @Override
    protected void updateBounds() {
//        ¯\_(ツ)_/¯
    }
}
