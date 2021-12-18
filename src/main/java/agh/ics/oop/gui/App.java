package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application implements IMapChangeObserver{
    private final GridPane gridL = new GridPane();
    private final GridPane gridR = new GridPane();
    private SimulationEngine engineL;
    private SimulationEngine engineR;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
//        primaryStage.setMaximized(true);
        primaryStage.setTitle("World Map Visualization");
        this.renderConfigurationScene(primaryStage);
        primaryStage.show();
    }

    // TODO configuration scene jako osobną klase
    // TODO grid jako osobną klase z metodą render()

    private void renderConfigurationScene(Stage primaryStage){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label height_label = new Label("Height:");
        grid.add(height_label, 0, 1);

        TextField height_input = new TextField();
        grid.add(height_input, 1, 1);

        Label width_label = new Label("Width:");
        grid.add(width_label, 0, 2);

        TextField width_input = new TextField();
        grid.add(width_input, 1, 2);

        Label jungle_ratio_label = new Label("Jungle ratio:");
        grid.add(jungle_ratio_label, 0, 3);

        TextField jungle_ratio_input = new TextField();
        grid.add(jungle_ratio_input, 1, 3);

        Label initial_energy_label = new Label("Initial animals' energy:");
        grid.add(initial_energy_label, 0, 4);

        TextField initial_energy_input = new TextField();
        grid.add(initial_energy_input, 1, 4);

        Label plant_energy_label = new Label("Plant energy:");
        grid.add(plant_energy_label, 0, 5);

        TextField plant_energy_input = new TextField();
        grid.add(plant_energy_input, 1, 5);

        Button btn = new Button("Go");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 6);

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);

        btn.setOnAction(e -> this.renderMainStage(this.primaryStage));
    }

    private void renderMainStage(Stage primaryStage){
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

    private void runSimulation(){
        try {
            this.engineL = new SimulationEngine(new UnboundedMap(10,10), SideIdentifier.LEFT);
            this.engineL.addObserver(this);
            Thread threadL = new Thread(this.engineL);
            threadL.start();

            this.engineR = new SimulationEngine(new UnboundedMap(10,10), SideIdentifier.RIGHT);
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
