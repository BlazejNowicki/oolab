package agh.ics.oop;

import javafx.application.Platform;

import java.util.LinkedList;

public class SimulationEngine implements Runnable{
    private final IMap map;
    private final SideIdentifier side;
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();
    private final LinkedList<IMapChangeObserver> observers;

    public SimulationEngine(IMap map, SideIdentifier side){
        this.side = side;
        this.map = map;
        this.observers = new LinkedList<>();
    }

    public IMap getMap() {
        return map;
    }

    public void addObserver(IMapChangeObserver observer){
        this.observers.push(observer);
    }

    public void mapChanged(){
        for (IMapChangeObserver observer: this.observers){
            observer.mapChanged(this.side);
        }
    }

    @Override
    public void run() {
        // handle pause and resume change to toggle later
        while (running) {
            System.out.println("Start of generation");
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
            this.simulateGeneration();
            try{
                Thread.sleep(2000);
            }catch(InterruptedException e){
                System.out.println(e);
            }
            System.out.println("End of generation");
            Platform.runLater(this::mapChanged);
        }
    }

    private void simulateGeneration(){
        this.map.moveElements();
    }

    public void stop() {
        this.running = false;
        resume();
    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }
}