package agh.ics.oop;

public abstract class AbstractMapElement implements IMapElement{
    protected Vector2d position;
    protected IMap map;

    public AbstractMapElement(Vector2d position, IMap map){
        this.position = position;
        this.map = map;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }
}
