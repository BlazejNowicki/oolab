package agh.ics.oop;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class AbstractMap implements IMap{
    protected final Vector2d lowerBound;
    protected final Vector2d upperBound;
    protected final Map<Vector2d, LinkedList<Animal>> animals;
    protected final Map<Vector2d, LinkedList<Plant>> plants;
    protected final int width;
    protected final int height;

    public AbstractMap(int width, int height){
        this.width = width;
        this.height = height;
        this.lowerBound = new Vector2d(0,0);
        this.upperBound = new Vector2d(width-1, height-1);
        this.animals = new HashMap<>();
        this.plants = new HashMap<>();
    }

    @Override
    public void moveElements() {
        // TODO dodać porusznie elementami
        System.out.println("Moving elements");
    }

    public void place(IMapElement new_element){
        Vector2d position = new_element.getPosition();
        if (new_element instanceof Animal){
            if (! animals.containsKey(position)) {
                animals.put(position, new LinkedList<>());
            }
            animals.get(position).push((Animal)new_element);
        } else if (new_element instanceof Plant){
            if (! plants.containsKey(position)) {
                plants.put(position, new LinkedList<>());
            }
            plants.get(position).push((Plant) new_element);
        }
    }

    @Override
    public IMapElement objectAt(Vector2d position){
//        Później zamienić na znajdowanie najsilniejszego
        if( animals.get(position) != null ){
            if (animals.get(position).size() > 0){
                return animals.get(position).getFirst();
            }
        } else if( plants.get(position) != null){
            if (plants.get(position).size() > 0){
                return plants.get(position).getFirst();
            }
        }
        return null;
    }

    @Override
    public boolean isOccupied(Vector2d position){
        if (animals.containsKey(position) && animals.get(position).size() > 0){
            return true;
        } else return plants.containsKey(position) && plants.get(position).size() > 0;
    }

    @Override
    public Vector2d getLowerBound(){
        return this.lowerBound;
    }

    @Override
    public Vector2d getUpperBound(){
        return this.upperBound;
    }
}
