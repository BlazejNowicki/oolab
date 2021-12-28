package agh.ics.oop;

import java.util.HashSet;

public class Tracker{
    private Animal alfa = null;
    private Integer alfa_death_time = null;
    private final HashSet<Animal> children = new HashSet<>();
    private final HashSet<Animal> descendants = new HashSet<>();
    private final IMap map;

    public Tracker(IMap map){
        this.map = map;
    }

    public void startTracking(Animal alfa){
        this.alfa_death_time = null;
        this.alfa = alfa;
        this.children.clear();
        this.descendants.clear();
    }

    public void wasBorn(Animal parentA, Animal parentB, Animal child) {
        if (parentA == alfa || parentB == alfa){
            this.children.add(child);
        } else if (this.children.contains(parentA) || this.children.contains(parentB)){
            this.descendants.add(child);
        } else if (this.descendants.contains(parentA) || this.descendants.contains(parentB)){
            this.descendants.add(child);
        }
    }

    public void died(Animal animal) {
        if (animal == alfa){
            this.alfa_death_time = this.map.getDate();
        }
    }

    public Animal getAlfa(){
        return this.alfa;
    }

    public int getChildrenSize(){
        return this.children.size();
    }

    public int getDescendantSize(){
        return this.descendants.size() + this.children.size();
    }

    public boolean alfaDead(){
        return this.alfa_death_time != null;
    }

    public int alfaDeathTime(){
        return this.alfa_death_time;
    }

    public boolean isTracking(){
        return this.alfa != null;
    }
}
