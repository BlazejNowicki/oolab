package agh.ics.oop.gui;

import agh.ics.oop.AbstractMapElement;
import agh.ics.oop.IMap;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.Vector2d;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MapGrid extends GridPane {
    // TODO pomyśleć o dodaniu jakis optyalizacji żeby nie ładować nowych zdjęć za każdym razem

    public void render(SimulationEngine engine){
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
            this.getColumnConstraints().add(new ColumnConstraints(50));
        }
        for (int i=0; lower.y + i <= upper.y+1; i++){
            this.getRowConstraints().add(new RowConstraints(50));
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
                if (map.isOccupied(new Vector2d(x,y))){
                    Object obj = map.objectAt(new Vector2d(x,y));
                    if (obj != null){
                        GuiMapElement item = new GuiMapElement((AbstractMapElement) obj);
                        item.setAlignment(Pos.CENTER);
                        GridPane.setHalignment(item, HPos.CENTER);
                        this.add(item, x-lower.x+1, upper.y+1-y);
                    }
                }
            }
        }
    }
}
