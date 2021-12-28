package agh.ics.oop;

import java.util.*;

public abstract class AbstractMap implements IMap{
    protected Map<Vector2d, LinkedList<Animal>> animals = new HashMap<>();
    protected final Map<Vector2d, Plant> plants = new HashMap<>();
    protected final Vector2d lowerBound;
    protected final Vector2d upperBound;
    protected final Vector2d jungle_lower;
    protected final Vector2d jungle_upper;
    protected final Random rand = new Random();
    protected final Tracker tracker = new Tracker(this);
    protected final Map<Genome, Integer> genome_tracker = new TreeMap<>();
    protected double avg_lifespan = 0;
    protected double number_of_dead = 0;
    protected int date;
    protected Genome dominant;
    protected final MapConfiguration conf;

    public AbstractMap(MapConfiguration conf){
        this.conf = conf;
        this.lowerBound = new Vector2d(0,0);
        this.upperBound = new Vector2d(conf.width()-1, conf.height()-1);

        int jungle_width = 2 * (int) Math.round((double)(conf.width()-1)*conf.jungle_ratio()/2);
        if(conf.width() % 2 == 0) jungle_width += 1;

        int jungle_height = 2 * (int) Math.round((double)(conf.height()-1)*conf.jungle_ratio()/2);
        if(conf.height() % 2 == 0) jungle_height += 1;

        this.jungle_lower = new Vector2d((conf.width()-jungle_width) / 2 , (conf.height() - jungle_height) / 2);
        this.jungle_upper = new Vector2d((conf.width()-jungle_width) / 2 + jungle_width,
                (conf.height() - jungle_height) / 2 + jungle_height);

        for (int i=0; i<conf.number_of_animals(); i++){
            Vector2d position = new Vector2d(
                    lowerBound.x + rand.nextInt(conf.width()),
                    lowerBound.y + rand.nextInt(conf.height())
            );
            this.place(new Animal(position, this, conf.initial_energy()));
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
            this.place(new Plant(empty_spot, this, this.conf.plant_energy()));
        }

        if (spots_outside.size() > 0){
            Vector2d empty_spot = spots_outside.get(rand.nextInt(spots_outside.size()));
            this.place(new Plant(empty_spot, this, this.conf.plant_energy()));
        }
    }

    @Override
    public void eatPlants() {
        for(int x=this.lowerBound.x; x <= this.upperBound.x; x++) {
            for (int y = lowerBound.y; y <= this.upperBound.y; y++) {
                Vector2d position = new Vector2d(x, y);
                if (this.animals.containsKey(position) && this.animals.get(position).size() > 0 && this.plants.containsKey(position)) {
                    LinkedList<Animal> list = new LinkedList<>(this.animals.get(position));
                    list.sort((a,b) -> b.getEnergy() - a.getEnergy());
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
        this.date += 1;

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
                        animal.decreaseEnergy(this.conf.move_energy());
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
                    list.sort((a,b) -> b.getEnergy() - a.getEnergy());
                    Animal animalA = list.get(0);
                    Animal animalB = list.get(1);
                    if ( animalA.getEnergy() >= conf.initial_energy()/2 && animalB.getEnergy() >= conf.initial_energy()){
                        int deltaA = (int) Math.round((double) animalA.getEnergy() / 4);
                        int deltaB = (int) Math.round((double) animalB.getEnergy() / 4);
                        animalA.decreaseEnergy(deltaA);
                        animalB.decreaseEnergy(deltaB);
                        Animal new_animal = new Animal(animalA, animalB, position, this, deltaA + deltaB);
                        list.add(new_animal);
                        this.add_genome(new_animal.getGenome());
                        this.tracker.wasBorn(animalA, animalB, new_animal);
                        animalA.gotChild();
                        animalB.gotChild();
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
                    for(Animal animal: this.animals.get(position)){
                        if (animal.getEnergy() <= 0){
                            this.avg_lifespan += animal.getAge();
                            this.remove_genome(animal.getGenome());
                            this.number_of_dead += 1;
                            this.tracker.died(animal);
                        }
                    }
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
            this.add_genome(((Animal) new_element).getGenome());
        } else if (new_element instanceof Plant){
            plants.put(position, (Plant) new_element);
        }
    }

    private void add_genome(Genome genome){
        if(!genome_tracker.containsKey(genome)){
            this.genome_tracker.put(genome, 0);
        }
        Integer v = genome_tracker.get(genome);
        this.genome_tracker.put(genome, v+1);
    }

    private void remove_genome(Genome genome){
        if(genome_tracker.containsKey(genome) && genome_tracker.get(genome) > 1){
            Integer v = genome_tracker.get(genome);
            this.genome_tracker.put(genome, v-1);
        } else {
            this.genome_tracker.remove(genome);
        }
    }

    @Override
    public IMapElement objectAt(Vector2d position){
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
    public void startTracking(Object obj){
        if(obj instanceof Animal){
            this.tracker.startTracking((Animal) obj);
        }
    }

    @Override
    public Tracker getTracker(){
        return this.tracker;
    }

    @Override
    public Statistics getStatistics() {
        double energy_sum = 0;
        int animal_count = 0, number_of_children=0;


        for(int x=this.lowerBound.x; x <= this.upperBound.x; x++) {
            for (int y = lowerBound.y; y <= this.upperBound.y; y++) {
                Vector2d position = new Vector2d(x,y);
                if (this.animals.containsKey(position)){
                    animal_count += this.animals.get(position).size();
                    for(Animal animal: this.animals.get(position)){
                        energy_sum += animal.getEnergy();
                        number_of_children += animal.getNumberOfChildren();
                    }
                }
            }
        }
        Map.Entry<Genome, Integer> maxEntry = null;
        for (Map.Entry<Genome, Integer> entry : genome_tracker.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        if (!genome_tracker.isEmpty())
            this.dominant = maxEntry.getKey();

        assert maxEntry != null;
        return new Statistics(
                animal_count,
                this.plants.size(),
                energy_sum / animal_count,
                avg_lifespan / number_of_dead,
                (double) number_of_children / animal_count,
                maxEntry.getKey()
            );
    }

    public boolean containsDominant(Vector2d position){
        if(!animals.containsKey(position)) return false;
        for(Animal animal: animals.get(position)){
            if(animal.getGenome().equals(this.dominant)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getDate() {
        return date;
    }

    @Override
    public MapConfiguration getConfiguration(){
        return this.conf;
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
        result.append("Genomes:\n");
        for(Map.Entry<Genome, Integer> entry: this.genome_tracker.entrySet()){
            result.append("K: " + entry.getKey() + " N: " + entry.getValue() + "\n");
        }
        result.append("}");
        return result.toString();
    }
}
