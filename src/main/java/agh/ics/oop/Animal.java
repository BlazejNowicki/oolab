package agh.ics.oop;

import java.util.Random;

public class Animal extends AbstractMapElement implements IMapElement{
    Random rand = new Random();

    public Animal(Vector2d position, IMap map) {
        super(position, map);
    }

    public Vector2d makeMove(){
        Vector2d move = new Vector2d(rand.nextInt(3)-1, rand.nextInt(3)-1);
        Vector2d new_position = this.position.add(move);
        if (this.map.canMoveTo(new_position)){
            this.position = this.map.newPosition(new_position);
        } else {
            System.out.println("Cant move there");
        }
        System.out.println(this.position);
        return this.position;
    }

    @Override
    public String getImagePath() {
        return "src/main/resources/grass.png";
    }
}
