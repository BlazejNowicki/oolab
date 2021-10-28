package agh.ics.oop;

import java.util.ArrayList;

public class SimulationEngine implements IEngine{
    private final MoveDirection[] moves;
    private final ArrayList<Animal> animals;
    private final IWorldMap map;

    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] positions) {
         this.moves = moves;
         this.animals = new ArrayList<>();
         this.map = map;
         for(Vector2d position: positions) {
             Animal new_animal = new Animal(map, position);
             if (map.place(new_animal) ) {
                 this.animals.add(new_animal);
             }
         }
    }

    @Override
    public void run() {
        if (animals.size() == 0) {
            return;
        }
        int animal_index = 0;
        for(MoveDirection move: this.moves) {
            this.animals.get(animal_index).move(move);
            animal_index = (animal_index+1)%animals.size();
        }
    }
}
