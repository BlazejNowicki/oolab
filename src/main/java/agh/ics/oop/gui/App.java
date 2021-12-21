package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application implements IMapChangeObserver{
    private final MapGrid gridL = new MapGrid();
    private final MapGrid gridR = new MapGrid();
    private SimulationEngine engineL;
    private SimulationEngine engineR;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setMaximized(true);
        primaryStage.setTitle("World Map Visualization");


        Scene scene = new Scene(new ConfigurationLayout(this));
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void renderMainStage(){
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        VBox sideL = new VBox();
        HBox input_sectionL = new HBox();
        Button start_buttonL = new Button("Start");
        sideL.getChildren().add(this.gridL);
        sideL.getChildren().add(input_sectionL);
        input_sectionL.getChildren().add(start_buttonL);
        input_sectionL.setPadding(new Insets(10, 10, 10, 10));

        VBox sideR = new VBox();
        HBox input_sectionR = new HBox();
        Button start_buttonR = new Button("Start");
        sideR.getChildren().add(this.gridR);
        sideR.getChildren().add(input_sectionR);
        input_sectionR.getChildren().add(start_buttonR);
        input_sectionR.setPadding(new Insets(10, 10, 10, 10));

        container.getChildren().add(sideL);
        container.getChildren().add(sideR);

        Scene main_scene = new Scene(container);
        primaryStage.setScene(main_scene);
        runSimulation();
        this.renderGrid(SideIdentifier.LEFT);
    }

    private void renderGrid(SideIdentifier side){
        switch (side) {
            case LEFT -> this.gridL.render(this.engineL);
            case RIGHT -> this.gridR.render(this.engineR);
        }
    }

    @Override
    public void mapChanged(SideIdentifier side) {
        this.renderGrid(side);
        System.out.println("Rendering grid...");
    }

    private void runSimulation(){
        try {
            this.engineL = new SimulationEngine(new UnboundedMap(10,10), SideIdentifier.LEFT);
            this.engineL.addObserver(this);
            Thread threadL = new Thread(this.engineL);
            threadL.start();

            this.engineR = new SimulationEngine(new BoundedMap(10,10), SideIdentifier.RIGHT);
            this.engineR.addObserver(this);
            Thread threadR = new Thread(this.engineR);
            threadR.start();

            this.renderGrid(SideIdentifier.LEFT);
            this.renderGrid(SideIdentifier.RIGHT);
        } catch ( IllegalArgumentException e){
            System.out.println(e);
        }
    }
}
