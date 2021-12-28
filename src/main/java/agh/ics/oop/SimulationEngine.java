package agh.ics.oop;

import java.util.LinkedList;

public class SimulationEngine implements Runnable{
    private final IMap map;
    private volatile boolean running = true;
    private volatile boolean paused = true;
    private final Object pauseLock = new Object();
    private final LinkedList<IMapChangeObserver> observers;
    private final int delay;
    private final String output_path;

    public SimulationEngine(IMap map, int delay, String output_path){
        this.map = map;
        this.observers = new LinkedList<>();
        this.delay = delay;
        this.output_path = output_path;
    }

    public IMap getMap() {
        return map;
    }

    public void addObserver(IMapChangeObserver observer){
        this.observers.push(observer);
    }

    public void mapChanged(){
        for (IMapChangeObserver observer: this.observers){
            observer.mapChanged();
        }
    }

    public boolean isPaused() {
        return paused;
    }

    @Override
    public void run() {
        while (running) {
            synchronized (pauseLock) {
                if (!running) {
                    break;
                }
                if (paused) {
                    try {
                        synchronized (pauseLock) {
                            pauseLock.wait();
                        }
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (!running) {
                        break;
                    }
                }
            }
            System.out.println("Start of generation");
            this.simulateGeneration();
            System.out.println("End of generation\n");
            this.mapChanged();
            try{
                Thread.sleep(this.delay);
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }
    }

    private void simulateGeneration(){
        this.map.removeDead();
        this.map.moveElements();
        this.map.eatPlants();
        this.map.reproduction();
        this.map.spawnPlants();
        System.out.println(this.map);
    }

    public void stop() {
        this.running = false;
        this.paused = true;
        toggle();
    }

    public void toggle() {
        if(!this.paused){
            this.paused = true;
        } else {
            synchronized (this.pauseLock) {
                this.paused = false;
                this.pauseLock.notifyAll();
            }
        }
    }

    public String getOutputPath(){
        return this.output_path;
    }
}