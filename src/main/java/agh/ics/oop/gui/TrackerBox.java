package agh.ics.oop.gui;

import agh.ics.oop.IMap;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.Tracker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TrackerBox extends VBox {
    public boolean started_tracking = false;
    public Label children_counter = new Label();
    public Label descendants_counter = new Label();
    public Label death_date_counter = new Label();
    public Label genome_counter = new Label();
    private MapBox parent;

    public TrackerBox(){
        super();
        this.setPadding(new Insets(10, 10, 10, 10));
        this.getChildren().add(new Label("Object to track hasn't been selected"));
    }

    public void setupGrid(){
        GridPane grid = new GridPane();
        this.getChildren().clear();
        this.getChildren().add(grid);
        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.CENTER);

        Label children_label = new Label("Number of children");
        grid.add(children_label, 0,0);
        GridPane.setMargin(children_label, new Insets(3,3,3,3));
        grid.add(this.children_counter, 1,0);
        GridPane.setMargin(children_counter, new Insets(3,3,3,3));

        Label descendants_label = new Label("Number of descendants");
        grid.add(descendants_label, 0,1);
        GridPane.setMargin(descendants_label, new Insets(3,3,3,3));
        grid.add(this.descendants_counter, 1,1);
        GridPane.setMargin(descendants_counter, new Insets(3,3,3,3));

        Label death_label = new Label("Date of death");
        grid.add(death_label, 0,2);
        GridPane.setMargin(death_label, new Insets(3,3,3,3));
        grid.add(this.death_date_counter, 1,2);
        GridPane.setMargin(death_date_counter, new Insets(3,3,3,3));


        Label genome_label = new Label("Genome of selected animal");
        grid.add(genome_label, 0,3);
        GridPane.setMargin(genome_label, new Insets(3,3,3,3));
        grid.add(this.genome_counter, 1,3);
        GridPane.setMargin(this.genome_counter, new Insets(3,3,3,3));
    }

    public void update(SimulationEngine engine){
        IMap map = engine.getMap();
        Tracker tracker = map.getTracker();

        if (tracker.isTracking()){
            if(!started_tracking){
                this.setupGrid();
                started_tracking = true;
            }
            this.genome_counter.setText(tracker.getAlfa().getGenome().toString());
            this.children_counter.setText(Integer.toString(tracker.getChildrenSize()));
            this.descendants_counter.setText(Integer.toString(tracker.getDescendantSize()));
            if(tracker.alfaDead()){
                this.death_date_counter.setText(Integer.toString(tracker.alfaDeathTime()));
            } else {
                this.death_date_counter.setText("Still alive");
            }
        }
    }
}
