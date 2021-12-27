package agh.ics.oop.gui;

import agh.ics.oop.IMapChangeObserver;
import agh.ics.oop.SimulationEngine;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MapBox extends HBox implements IMapChangeObserver {
    private MapGrid grid = new MapGrid();
    private StatisticsBox stat_box= new StatisticsBox();
    private SimulationEngine engine;

    public MapBox(){
        super();
//        ScrollPane scroll = new ScrollPane(this.stat_box);
        this.getChildren().add(this.stat_box);

        HBox input_section = new HBox();
        Button start_stop = new Button("Start");
        input_section.getChildren().add(start_stop);
        input_section.setPadding(new Insets(10, 10, 10, 10));

        VBox map_button = new VBox();
        map_button.getChildren().add(this.grid);
        map_button.getChildren().add(input_section);
        this.getChildren().add(map_button);

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
        Platform.runLater(() -> {
            this.grid.render(this.engine);
            this.stat_box.update(this.engine);
        });
    }

    public void showGrid(){
        this.grid.render(this.engine);
        this.stat_box.update(this.engine);
    }
}
