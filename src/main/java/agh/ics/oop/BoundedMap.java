package agh.ics.oop;

public class BoundedMap extends AbstractMap implements IMap{

    public BoundedMap(int width, int height) {
        super(width, height);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.precedes( this.upperBound ) && position.follows( this.lowerBound );
    }

    @Override
    public Vector2d newPosition(Vector2d position) {
        if (position.precedes( this.upperBound ) && position.follows( this.lowerBound )) {
            return position;
        }
        return null;
    }
}
