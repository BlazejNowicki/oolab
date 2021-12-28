package agh.ics.oop.gui;

import agh.ics.oop.IMapChangeObserver;
import agh.ics.oop.SimulationEngine;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MapBox extends HBox implements IMapChangeObserver {
    private MapGrid grid = new MapGrid(this);
    private StatisticsBox stat_box= new StatisticsBox();
    private TrackerBox tracker_box = new TrackerBox();
    private SimulationEngine engine;
    private boolean show_dominant = false;

    public MapBox(){
        super();
        this.getChildren().add(this.stat_box);

        HBox input_section = new HBox();
        Button start_stop = new Button("Start");
        Button show_dominant_genome = new Button("Show dominant genome");
        input_section.getChildren().add(start_stop);
        input_section.getChildren().add(show_dominant_genome);
        input_section.setPadding(new Insets(10, 10, 10, 10));

        VBox map_button = new VBox();
        map_button.getChildren().add(this.grid);
        map_button.getChildren().add(input_section);
        map_button.getChildren().add(this.tracker_box);
        this.getChildren().add(map_button);

        start_stop.setOnAction( e -> {
            this.engine.toggle();
            if (engine.isPaused() ){
                start_stop.setText("Resume");
            } else {
                start_stop.setText("Pause");
            }
        });

        show_dominant_genome.setOnAction(e->{
            show_dominant = !show_dominant;
            if (show_dominant){
                show_dominant_genome.setText("Hide dominant genome");
            } else {
                show_dominant_genome.setText("Show dominant genome");
            }
            mapChanged();
        });
    }

    public void setEngine(SimulationEngine engine) {
        this.engine = engine;
    }

    @Override
    public void mapChanged() {
        Platform.runLater(() -> {
            this.grid.render(this.engine, this.show_dominant);
            this.stat_box.update(this.engine);
            this.tracker_box.update(this.engine);
        });
    }

    public void showGrid(){
        this.grid.render(this.engine, this.show_dominant);
        this.stat_box.update(this.engine);
    }
}
