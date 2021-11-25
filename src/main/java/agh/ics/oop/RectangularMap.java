package agh.ics.oop;

public class RectangularMap extends AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
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
        return this.animals.get(position);
    }

    @Override
    protected void updateBounds() {
//        Map limits are defined at the beginning.
    }
}
