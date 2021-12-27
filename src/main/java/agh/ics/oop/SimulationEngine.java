package agh.ics.oop;

import javafx.application.Platform;

import java.util.LinkedList;

public class SimulationEngine implements Runnable{
    private final IMap map;
    private volatile boolean running = true;
    private volatile boolean paused = true;
    private final Object pauseLock = new Object();
    private final LinkedList<IMapChangeObserver> observers;
    private final int delay;

    public SimulationEngine(IMap map, int delay){
        this.map = map;
        this.observers = new LinkedList<>();
        this.delay = delay;
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
}