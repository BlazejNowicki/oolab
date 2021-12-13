package agh.ics.oop;

public class Grass extends AbstractMapObject{

    public Grass(Vector2d position) {
        this.position = position;
    }

    public String toString() {
        return "*";
    }

    @Override
    public String getResourcePath() {
        return "src/main/resources/grass.png";
    }
}
