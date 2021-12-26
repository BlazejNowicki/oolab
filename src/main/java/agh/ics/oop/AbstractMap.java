package agh.ics.oop;

import java.util.*;

public abstract class AbstractMap implements IMap{
    protected final Vector2d lowerBound;
    protected final Vector2d upperBound;
    protected Map<Vector2d, LinkedList<Animal>> animals;
    protected final Map<Vector2d, Plant> plants;
    protected final int width;
    protected final int height;
    protected Vector2d jungle_lower;
    protected Vector2d jungle_upper;
    protected Random rand;
    protected final int plant_energy;

    public AbstractMap(MapConfiguration conf){
        // TODO !!! teraz dodać generownie zwierząt na losowych miejscach na początku wymulacji (ich liczba jako parametr)
        this.width = conf.width;
        this.height = conf.height;
        this.lowerBound = new Vector2d(0,0);
        this.upperBound = new Vector2d(width-1, height-1);
        this.animals = new HashMap<>();
        this.plants = new HashMap<>();
        this.rand = new Random();
        this.plant_energy = conf.plant_energy;

        // TODO Czy to ma wgl sensowne rozwiązanie? VVV
        int jungle_width = 2 * (int) Math.round((double)(conf.width-1)*conf.jungle_ratio/2);
        if(conf.width % 2 == 0) jungle_width += 1;

        int jungle_height = 2 * (int) Math.round((double)(conf.height-1)*conf.jungle_ratio/2);
        if(conf.height % 2 == 0) jungle_height += 1;

        this.jungle_lower = new Vector2d((conf.width-jungle_width) / 2 , (conf.height - jungle_height) / 2);
        this.jungle_upper = new Vector2d((conf.width-jungle_width) / 2 + jungle_width,
                (conf.height - jungle_height) / 2 + jungle_height);

        for (int i=0; i<conf.number_of_animals; i++){
            Vector2d position = new Vector2d(
                    lowerBound.x + rand.nextInt(conf.width),
                    lowerBound.y + rand.nextInt(conf.height)
            );
            this.place(new Animal(position, this, conf.initial_energy));
        }
    }

    public boolean isJungle(Vector2d position){
        return position.precedes(this.jungle_upper) && position.follows(this.jungle_lower);
    }

    public abstract Vector2d newPosition(Vector2d position);

    @Override
    public void spawnPlants() {
        LinkedList<Vector2d> spots_jungle = new LinkedList<>();
        LinkedList<Vector2d> spots_outside = new LinkedList<>();
        for(int x=this.lowerBound.x; x <= this.upperBound.x; x++) {
            for (int y = lowerBound.y; y <= this.upperBound.y; y++) {
                Vector2d position = new Vector2d(x,y);
                if (!this.isOccupied(position)){
                    if(this.isJungle(position)){
                        spots_jungle.add(position);
                    } else {
                        spots_outside.add(position);
                    }
                }
            }
        }
        if (spots_jungle.size() > 0){
            Vector2d empty_spot = spots_jungle.get(rand.nextInt(spots_jungle.size()));
            this.place(new Plant(empty_spot, this, this.plant_energy));
        }

        if (spots_outside.size() > 0){
            Vector2d empty_spot = spots_outside.get(rand.nextInt(spots_outside.size()));
            this.place(new Plant(empty_spot, this, this.plant_energy));
        }
    }

    @Override
    public void eatPlants() {
        for(int x=this.lowerBound.x; x <= this.upperBound.x; x++) {
            for (int y = lowerBound.y; y <= this.upperBound.y; y++) {
                Vector2d position = new Vector2d(x, y);
                if (this.animals.containsKey(position) && this.animals.get(position).size() > 0 && this.plants.containsKey(position)) {
                    LinkedList<Animal> list = new LinkedList<>(this.animals.get(position));
                    list.sort((a,b) -> a.getEnergy() - b.getEnergy()); // TODO to sortownie można jeszcze przemyśleć
                    int top_energy = list.get(0).getEnergy();
                    list.removeIf(e -> e.getEnergy() < top_energy);
                    Plant plant = this.plants.get(position);
                    for(Animal animal: list){
                        animal.increaseEnergy((int) Math.round((double)plant.getEnergy() / list.size()));
                    }
                    this.plants.remove(position);
                }
            }
        }
    }

    @Override
    public void moveElements() {
        Map<Vector2d, LinkedList<Animal>> moved_animals = new HashMap<>();

        for(int x=this.lowerBound.x; x <= this.upperBound.x; x++){
            for (int y=lowerBound.y; y <= this.upperBound.y; y++){
                Vector2d position = new Vector2d(x,y);
                if (this.animals.containsKey(position)){
                    for (Animal animal: this.animals.get(position)){
                        Vector2d new_position = animal.makeMove();
                        if (!moved_animals.containsKey(new_position)) {
                            moved_animals.put(new_position, new LinkedList<>());
                        }
                        moved_animals.get(new_position).push(animal);
                        if (! position.equals(new_position))
                            animal.decreaseEnergy(1);
                    }
                }
            }
        }
        this.animals = moved_animals;
    }

    @Override
    public void reproduction(){
        for(int x=this.lowerBound.x; x <= this.upperBound.x; x++){
            for (int y=lowerBound.y; y <= this.upperBound.y; y++){
                Vector2d position = new Vector2d(x, y);
                if (this.animals.containsKey(position) && this.animals.get(position).size() >= 2){
                    LinkedList<Animal> list = this.animals.get(position);
                    list.sort((a,b) -> a.getEnergy() - b.getEnergy()); // TODO to sortownie można jeszcze przemyśleć
                    Animal animalA = list.get(0);
                    Animal animalB = list.get(1);
                    if ( animalA.getEnergy() > 4 && animalB.getEnergy() > 4){
                        int deltaA = (int) Math.round((double) animalA.getEnergy() / 4);
                        int deltaB = (int) Math.round((double) animalB.getEnergy() / 4);
                        animalA.decreaseEnergy(deltaA);
                        animalB.decreaseEnergy(deltaB);
                        list.add(new Animal(animalA, animalB, position, this, deltaA + deltaB));
                    }
                }
            }
        }
    }

    @Override
    public void removeDead() {
        for(int x=this.lowerBound.x; x <= this.upperBound.x; x++){
            for (int y=lowerBound.y; y <= this.upperBound.y; y++){
                Vector2d position = new Vector2d(x, y);
                if (this.animals.containsKey(position)){
                    this.animals.get(position).removeIf(animal -> animal.getEnergy() <= 0);
                }
            }
        }
    }

    public void place(IMapElement new_element){
        Vector2d position = new_element.getPosition();
        if (new_element instanceof Animal){
            if (! animals.containsKey(position)) {
                animals.put(position, new LinkedList<>());
            }
            animals.get(position).push((Animal)new_element);
        } else if (new_element instanceof Plant){
            plants.put(position, (Plant) new_element);
        }
    }

    @Override
    public IMapElement objectAt(Vector2d position){
//       TODO Później zamienić na znajdowanie najsilniejszego
        if( animals.get(position) != null ){
            if (animals.get(position).size() > 0){
                return animals.get(position).getFirst();
            }
        } else if( plants.get(position) != null){
            return plants.get(position);
        }
        return null;
    }

    @Override
    public boolean isOccupied(Vector2d position){
        if (animals.containsKey(position) && animals.get(position).size() > 0){
            return true;
        } else return plants.containsKey(position);
    }

    @Override
    public Vector2d getLowerBound(){
        return this.lowerBound;
    }

    @Override
    public Vector2d getUpperBound(){
        return this.upperBound;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Map{ \n");
        for(int x=this.lowerBound.x; x <= this.upperBound.x; x++){
            for (int y=lowerBound.y; y <= this.upperBound.y; y++){
                Vector2d position = new Vector2d(x, y);
                if (this.animals.containsKey(position) && this.animals.get(position).size() > 0){
                    for(Animal animal: this.animals.get(position)){
                        result.append(animal.toString());
                    }
                }
            }
        }
        result.append("}");
        return result.toString();
    }
}
