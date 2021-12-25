package agh.ics.oop.gui;

import agh.ics.oop.IMapChangeObserver;
import agh.ics.oop.SimulationEngine;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MapBox extends VBox implements IMapChangeObserver {
    private MapGrid grid = new MapGrid();
    private SimulationEngine engine;

    public MapBox(){
        super();

        HBox input_section = new HBox();
        Button start_stop = new Button("Start");
        this.getChildren().add(this.grid);
        this.getChildren().add(input_section);
        input_section.getChildren().add(start_stop);
        input_section.setPadding(new Insets(10, 10, 10, 10));


        start_stop.setOnAction( e -> {
            this.engine.toggle();
            if (engine.isPaused() ){
                start_stop.setText("Resume");
            } else {
                start_stop.setText("Pause");
            }
        });
    }

    public void setEngine(SimulationEngine engine) {
        this.engine = engine;
    }

    @Override
    public void mapChanged() {
        this.grid.render(this.engine);
    }

    public void showGrid(){
        this.grid.render(this.engine);
    }
}
