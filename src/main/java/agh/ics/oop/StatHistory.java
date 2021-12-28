package agh.ics.oop;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class StatHistory {
    private final LinkedList<Double> animals = new LinkedList<>();
    private final LinkedList<Double> plants = new LinkedList<>();
    private final LinkedList<Double> energy = new LinkedList<>();
    private final LinkedList<Double> lifespan = new LinkedList<>();
    private final LinkedList<Double> children = new LinkedList<>();

    public void addRecord(int animals, int plants, double energy, double lifespan, double children){
        this.animals.add((double)animals);
        this.plants.add((double)plants);
        this.energy.add(energy);
        this.lifespan.add(lifespan);
        this.children.add(children);
    }

    public void saveToFile(String path){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write("NumberOfAnimals, NumberOfPlants, AvgEnergy, AvgLifespan, AvgChildren\n");
            LinkedList<Double> animals = new LinkedList<>(this.animals);
            LinkedList<Double> plants = new LinkedList<>(this.plants);
            LinkedList<Double> energy = new LinkedList<>(this.energy);
            LinkedList<Double> lifespan = new LinkedList<>(this.lifespan);
            LinkedList<Double> children = new LinkedList<>(this.children);


            while (!(animals.isEmpty() || plants.isEmpty() || energy.isEmpty() || lifespan.isEmpty() || children.isEmpty())){
                writer.write(animals.poll() + "," +
                        plants.poll() + "," +
                        energy.poll() + "," +
                        lifespan.poll() + "," +
                        children.poll() + "\n"
                );
            }
            writer.write(average(this.animals) + "," +
                    average(this.plants) + "," +
                    average(this.energy) + "," +
                    average(this.lifespan) + "," +
                    average(this.children)
            );

            writer.close();
        } catch (IOException e){
            System.out.println(e);
        }
    }

    public double average(LinkedList<Double> list){
        double sum = 0;
        for(Double value: list){
            sum += value;
        }
        return sum / list.size();
    }
}
