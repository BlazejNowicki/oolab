package agh.ics.oop;

public abstract class AbstractMapElement implements IMapElement{
    protected Vector2d position;
    protected int energy;
    protected IMap map;

    public AbstractMapElement(Vector2d position, IMap map, int energy){
        this.position = position;
        this.energy = energy;
        this.map = map;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }
}
