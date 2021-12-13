package agh.ics.oop;

import java.util.ArrayList;
import java.util.LinkedList;

public class SimulationEngine implements IEngine, Runnable{
    private final MoveDirection[] moves;
    private final ArrayList<Animal> animals;
    private final IWorldMap map;
    private final LinkedList<IMapChangeObserver> observers;
    private final int delay;

    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] positions, int delay) {
         this.moves = moves;
         this.animals = new ArrayList<>();
         this.observers = new LinkedList<>();
         this.map = map;
         this.delay = delay;
         for(Vector2d position: positions) {
             Animal new_animal = new Animal(map, position);
             map.place(new_animal);
             this.animals.add(new_animal);
         }
    }

    public void addObserver(IMapChangeObserver new_observer){
        this.observers.add(new_observer);
    }

    public void mapChanged(){
        for (IMapChangeObserver observer: this.observers){
            observer.mapChanged();
        }
    }

    @Override
    public void run() {
        if (animals.size() == 0) {
            return;
        }
        int animal_index = 0;
        for(MoveDirection move: this.moves) {
            try{
                Thread.sleep(this.delay);
            }catch(InterruptedException e){
                System.out.println(e);
            }
            this.animals.get(animal_index).move(move);
            animal_index = (animal_index+1)%animals.size();
            mapChanged();
        }
    }
}
