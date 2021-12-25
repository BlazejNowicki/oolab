package agh.ics.oop;

public class MapConfiguration {
    public final int width, height, initial_energy, plant_energy, delay, move_energy;
    public final double jungle_ratio;


    public MapConfiguration(int width, int height,double jungle_ratio, int initial_energy, int plant_energy, int move_energy, int delay) {
        this.width = width;
        this.height = height;
        this.initial_energy = initial_energy;
        this.plant_energy = plant_energy;
        this.jungle_ratio = jungle_ratio;
        this.move_energy = move_energy;
        this.delay = delay;
    }
}
