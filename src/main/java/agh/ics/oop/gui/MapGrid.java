package agh.ics.oop.gui;

import agh.ics.oop.AbstractMapElement;
import agh.ics.oop.IMap;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.Vector2d;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.Map;

public class MapGrid extends GridPane {
    private final MapBox parent;
    private final Map<String, Image> cache = new HashMap<>();

    public MapGrid(MapBox parent){
        super();
        this.parent = parent;
    }

    public void render(SimulationEngine engine, boolean show_dominant){
        IMap map = engine.getMap();

        this.setGridLinesVisible(false);
        this.getChildren().clear();
        this.getColumnConstraints().clear();
        this.getRowConstraints().clear();

        this.setMinSize(400, 200);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setGridLinesVisible(true);

        Vector2d lower = map.getLowerBound();
        Vector2d upper = map.getUpperBound();

        // columns and rows settings
        for (int i=0; lower.x + i <= upper.x+1; i++){
            this.getColumnConstraints().add(new ColumnConstraints(30));
        }
        for (int i=0; lower.y + i <= upper.y+1; i++){
            this.getRowConstraints().add(new RowConstraints(30));
        }

        // axis description
        Label l = new Label("y/x");
        l.setAlignment(Pos.CENTER);
        this.add(l, 0, 0);

        for (int i = lower.x; i <= upper.x; i++){
            Label new_element = new Label(Integer.toString(i));
            GridPane.setHalignment(new_element, HPos.CENTER);
            this.add(new_element, i - lower.x+1, 0);
        }

        for (int i = lower.y; i <= upper.y; i++){
            Label new_element = new Label(Integer.toString(i));
            GridPane.setHalignment(new_element, HPos.CENTER);
            this.add(new_element, 0, upper.y+1-i);
        }

        // adding objects to map
        for (int x = lower.x; x<=upper.x; x++){
            for (int y = lower.y; y<=upper.y; y++){
                Vector2d position = new Vector2d(x,y);
                StackPane pane = new StackPane();
                if (map.isOccupied(position)){
                    Object obj = map.objectAt(new Vector2d(x,y));
                    if (obj != null){
                        GuiMapElement item = new GuiMapElement((AbstractMapElement) obj, cache);
                        pane.getChildren().add(item);
                    }
                }
                if (map.isJungle(position)){
                    pane.setStyle("-fx-background-color: #86d46c; -fx-border-color: gray");
                } else {
                    pane.setStyle("-fx-background-color: #af9c6a; -fx-border-color: gray");
                }

                if (show_dominant && map.containsDominant(position)){
                    pane.setStyle("-fx-border-color: red; -fx-border-width: 3");
                }

                StackPane.setAlignment(pane, Pos.CENTER);
                pane.setOnMouseClicked(e -> {
                    map.startTracking(map.objectAt(position));
                    parent.mapChanged();
                });
                this.add(pane, x-lower.x+1, upper.y+1-y);
            }
        }
    }
}
