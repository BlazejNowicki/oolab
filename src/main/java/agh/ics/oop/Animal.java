package agh.ics.oop;

import java.lang.module.Configuration;
import java.util.Random;

public class Animal extends AbstractMapElement implements IMapElement{
    private final Direction direction;
    private final Genome genome;
    private int number_of_children = 0;
    private int age = 0;


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
        this.age += 1;
        return this.position;
    }

    public void decreaseEnergy(int delta){
        this.energy = this.energy - delta;
    }

    public void increaseEnergy(int delta){
        this.energy += delta;
    }

    @Override
    public String getImagePath() {
        String energy_level;
        int initial_energy = this.map.getConfiguration().initial_energy();
        if (energy > initial_energy){
            energy_level = "high/";
        } else if(energy > initial_energy/2){
            energy_level = "mid/";
        } else {
            energy_level = "low/";
        }
        return "src/main/resources/" + energy_level + direction.sourcePath();
    }

    public Genome getGenome() {
        return genome;
    }

    public void gotChild(){
        this.number_of_children += 1;
    }

    public int getNumberOfChildren(){
        return this.number_of_children;
    }

    public int getAge(){
        return this.age;
    }

    @Override
    public String toString() {
        return "Animal {" +
                " Pos: " + this.position.toString() +
                " Eng: " + this.energy +
                " Dir: " + this.direction +
                " Gen: " + this.genome.toString() +
                "}\n";
    }
}
