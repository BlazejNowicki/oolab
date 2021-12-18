package agh.ics.oop;

public class Animal extends AbstractMapElement implements IMapElement{
    public Animal(Vector2d position) {
        super(position);
    }

    @Override
    public String getImagePath() {
        return "src/main/resources/grass.png";
    }
}
