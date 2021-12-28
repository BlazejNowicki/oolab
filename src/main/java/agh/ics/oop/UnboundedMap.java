package agh.ics.oop;

public class UnboundedMap extends AbstractMap implements IMap{
    public UnboundedMap(MapConfiguration conf){
        super(conf);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    @Override
    public Vector2d newPosition(Vector2d position) {
        int x = position.x % this.conf.width();
        int y = position.y % this.conf.height();

        if (x < 0) x += this.conf.width();
        if (y < 0) y += this.conf.height();

        return new Vector2d(x, y);
    }
}
