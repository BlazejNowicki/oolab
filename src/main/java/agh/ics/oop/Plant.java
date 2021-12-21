package agh.ics.oop;

public class Plant extends AbstractMapElement implements IMapElement{

    public Plant(Vector2d position, IMap map) {
        super(position, map);
    }

    @Override
    public String getImagePath() {
        return "src/main/resources/grass.png";
    }
}
