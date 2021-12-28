package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application{
    private SimulationEngine engineL;
    private SimulationEngine engineR;
    private MapBox left_side;
    private MapBox right_side;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setMaximized(true);
        primaryStage.setTitle("World Map Visualization");


        Scene scene = new Scene(new ConfigurationForm(this));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop(){
        this.engineR.stop();
        this.engineL.stop();
    }


    public void renderMainStage(MapConfiguration conf){
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        this.left_side = new MapBox();
        this.right_side = new MapBox();

        container.getChildren().add(this.left_side);
        container.getChildren().add(this.right_side);

        Scene main_scene = new Scene(container);
        primaryStage.setScene(main_scene);
        runSimulation(conf);
    }


    public void runSimulation(MapConfiguration conf){
        try {
            this.engineL = new SimulationEngine(new UnboundedMap(conf), conf.delay(), "stat_map_L.csv");
            this.engineL.addObserver(this.left_side);
            this.left_side.setEngine(this.engineL);
            Thread threadL = new Thread(this.engineL);
            threadL.start();

            this.engineR = new SimulationEngine(new BoundedMap(conf), conf.delay(), "stat_map_R.csv");
            this.engineR.addObserver(this.right_side);
            this.right_side.setEngine(this.engineR);
            Thread threadR = new Thread(this.engineR);
            threadR.start();

            this.right_side.showGrid();
            this.left_side.showGrid();
        } catch ( IllegalArgumentException e){
            System.out.println(e);
        }
    }
}
