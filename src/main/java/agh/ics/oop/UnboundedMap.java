package agh.ics.oop;

public class UnboundedMap extends AbstractMap implements IMap{
    public UnboundedMap(MapConfiguration conf){
        super(conf);
        this.place(new Animal(new Vector2d(0,0), this, conf.initial_energy));
        this.place(new Animal(new Vector2d(0,0), this, conf.initial_energy));
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    @Override
    public Vector2d newPosition(Vector2d position) {
        int x = position.x % this.width;
        int y = position.y % this.height;

        if (x < 0) x += this.width;
        if (y < 0) y += this.height;

        return new Vector2d(x, y);
    }
}
