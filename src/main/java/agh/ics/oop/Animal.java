package agh.ics.oop;

import java.util.Random;

public class Animal extends AbstractMapElement implements IMapElement{
    Random rand = new Random();
    private final Direction direction;
    private final Genome genome;


    public Animal(Vector2d position, IMap map, int energy) {
        super(position, map, energy);
        this.direction = new Direction();
        this.genome = new Genome();
    }

    public Animal(Animal animalA, Animal animalB, Vector2d position, IMap map, int energy){
        super(position, map, energy);
        this.direction = new Direction();
        this.genome = new Genome(animalA, animalB);
    }

    public Vector2d makeMove(){
        Vector2d move = this.direction.turn(this.genome);
        Vector2d new_position = this.position.add(move);
        if (this.map.canMoveTo(new_position)){
            this.position = this.map.newPosition(new_position);
        } else {
            System.out.println("Cant move there");
        }
        return this.position;
    }

    public void decreaseEnergy(int delta){
        this.energy = this.energy - delta;
    }

    @Override
    public String getImagePath() {
        return "src/main/resources/grass.png";
    }

    public Genome getGenome() {
        return genome;
    }

    @Override
    public String toString() {
        return "Animal {" +
                " Pos: " + this.position.toString() +
                " Eng: " + Integer.toString(this.energy) +
                " Dir: " + this.direction +
                " Gen: " + this.genome.toString() +
                "}\n";
    }
}
