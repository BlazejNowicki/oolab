package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application implements IMapChangeObserver{
    private final GridPane gridL = new GridPane();
    private final GridPane gridR = new GridPane();
    private SimulationEngine engineL;
    private SimulationEngine engineR;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("World Map Visualization");
        VBox container = new VBox();
        HBox input_section = new HBox();
        Button start_button = new Button("Start");
        container.getChildren().add(this.gridL);
        container.getChildren().add(input_section);
        input_section.getChildren().add(start_button);
        input_section.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(container);
        this.renderGrid(SideIdentifier.LEFT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void renderGrid(SideIdentifier side){
        GridPane grid = null;
        IMap map = null;

        switch (side) {
            case LEFT -> {
                grid = this.gridL;
                map = this.engineL.getMap();
            }
            case RIGHT -> {
                grid = this.gridR;
                map = this.engineR.getMap();
            }
        }

        grid.setGridLinesVisible(false);
        grid.getChildren().clear();
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();

        grid.setMinSize(400, 200);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setGridLinesVisible(true);

        Vector2d lower = map.getLowerBound();
        Vector2d upper = map.getUpperBound();

        // columns and rows settings
        for (int i=0; lower.x + i <= upper.x+1; i++){
            grid.getColumnConstraints().add(new ColumnConstraints(50));
        }
        for (int i=0; lower.y + i <= upper.y+1; i++){
            grid.getRowConstraints().add(new RowConstraints(50));
        }

        // axis description
        Label l = new Label("y/x");
        l.setAlignment(Pos.CENTER);
        grid.add(l, 0, 0);

        for (int i = lower.x; i <= upper.x; i++){
            Label new_element = new Label(Integer.toString(i));
            GridPane.setHalignment(new_element, HPos.CENTER);
            grid.add(new_element, i - lower.x+1, 0);
        }

        for (int i = lower.y; i <= upper.y; i++){
            Label new_element = new Label(Integer.toString(i));
            GridPane.setHalignment(new_element, HPos.CENTER);
            grid.add(new_element, 0, upper.y+1-i);
        }

        // adding objects to map
        for (int x = lower.x; x<=upper.x; x++){
            for (int y = lower.y; y<=upper.y; y++){
                if (map.isOccupied(new Vector2d(x,y))){
                    Object obj = map.objectAt(new Vector2d(x,y));
                    if (obj != null){
                        GuiElementBox item = new GuiElementBox((AbstractMapElement) obj);
                        item.setAlignment(Pos.CENTER);
                        GridPane.setHalignment(item, HPos.CENTER);
                        grid.add(item, x-lower.x+1, upper.y+1-y);
                    }
                }
            }
        }
    }

    @Override
    public void mapChanged(SideIdentifier side) {
        this.renderGrid(side);
        System.out.println("Rendering grid...");
    }

    @Override
    public void init() {
        try {
            IMap map = new UnboundedMap(10, 10);
            this.engineL = new SimulationEngine(map, SideIdentifier.LEFT);
            this.engineL.addObserver(this);
            Thread thread = new Thread(this.engineL);
            thread.start();
        } catch ( IllegalArgumentException e){
            System.out.println(e);
        }
    }
}
