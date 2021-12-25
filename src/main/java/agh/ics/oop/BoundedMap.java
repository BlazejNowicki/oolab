package agh.ics.oop;

public class BoundedMap extends AbstractMap implements IMap{

    public BoundedMap(MapConfiguration conf) {
        super(conf);
        this.place(new Animal(new Vector2d(0,0), this, conf.initial_energy));
        this.place(new Animal(new Vector2d(0,0), this, conf.initial_energy));
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
