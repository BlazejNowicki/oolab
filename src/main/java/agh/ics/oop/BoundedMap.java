package agh.ics.oop;

public class BoundedMap extends AbstractMap implements IMap{

    public BoundedMap(MapConfiguration conf) {
        super(conf);
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
