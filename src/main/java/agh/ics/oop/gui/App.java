package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application implements IMapChangeObserver {
    private AbstractWorldMap map;
    private final GridPane grid = new GridPane();
    private Thread thread;
    private SimulationEngine engine;
    private TextField moves_input;
    private boolean started_flag = false;

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("World Map Visualization");
        VBox container = new VBox();
        HBox input_section = new HBox();
        Button start_button = new Button("Start");
        start_button.setOnAction((value) -> {
            this.engine.setMoves(this.moves_input.getText());
            if(! started_flag){
                this.thread.start();
                this.started_flag = true;
            }
        });
        this.moves_input = new TextField();
        container.getChildren().add(this.grid);
        container.getChildren().add(input_section);
        input_section.getChildren().add(this.moves_input);
        input_section.getChildren().add(start_button);
        input_section.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(container);
        this.renderGrid();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void renderGrid(){
        this.grid.getChildren().clear();
        this.grid.getColumnConstraints().clear();
        this.grid.getRowConstraints().clear();

        this.grid.setMinSize(400, 200);
        this.grid.setPadding(new Insets(10, 10, 10, 10));
        this.grid.setGridLinesVisible(true);

        Vector2d lower = this.map.getLower_bound();
        Vector2d upper = this.map.getUpper_bound();

        // columns and rows settings
        for (int i=0; lower.x + i <= upper.x+1; i++){
            this.grid.getColumnConstraints().add(new ColumnConstraints(50));
        }
        for (int i=0; lower.y + i <= upper.y+1; i++){
            this.grid.getRowConstraints().add(new RowConstraints(50));
        }

        // axis description
        Label l = new Label("y/x");
        l.setAlignment(Pos.CENTER);
        this.grid.add(l, 0, 0);

        for (int i = lower.x; i <= upper.x; i++){
            Label new_element = new Label(Integer.toString(i));
            GridPane.setHalignment(new_element, HPos.CENTER);
            this.grid.add(new_element, i - lower.x+1, 0);
        }

        for (int i = lower.y; i <= upper.y; i++){
            Label new_element = new Label(Integer.toString(i));
            GridPane.setHalignment(new_element, HPos.CENTER);
            this.grid.add(new_element, 0, upper.y+1-i);
        }

        // adding objects to map
        for (int x = lower.x; x<=upper.x; x++){
            for (int y = lower.y; y<=upper.y; y++){
                if (map.isOccupied(new Vector2d(x,y))){
                    Object obj = this.map.objectAt(new Vector2d(x,y));
                    if (obj != null){
                        GuiElementBox item = new GuiElementBox((AbstractMapObject) obj);
                        item.setAlignment(Pos.CENTER);
                        GridPane.setHalignment(item, HPos.CENTER);
                        this.grid.add(item, x-lower.x+1, upper.y+1-y);
                    }
                }
            }
        }
    }


    @Override
    public void init() throws Exception {
        try {
            String[] moves = {"r", "f","f", "f"};
            MoveDirection[] directions = new OptionsParser().parse(moves);
            this.map = new GrassField(30);
            Vector2d[] positions = { new Vector2d(-2,2)};
            this.engine = new SimulationEngine(directions, this.map, positions, 500);
            this.engine.addObserver(this);
            this. thread = new Thread(this.engine);
//            thread.start();
        } catch ( IllegalArgumentException e){
            System.out.println(e);
        }
    }



    @Override
    public void mapChanged() {
//        grid.setGridLinesVisible(false);
        Platform.runLater(() -> {
            this.grid.setGridLinesVisible(false);
            renderGrid();
        });
    }
}
