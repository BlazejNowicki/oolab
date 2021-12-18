package agh.ics.oop;

public class Plant extends AbstractMapElement implements IMapElement{
    public Plant(Vector2d position) {
        super(position);
    }

    @Override
    public String getImagePath() {
        return "src/main/resources/grass.png";
    }
}
