package agh.ics.oop.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ConfigurationLayout extends GridPane {
    public ConfigurationLayout(App parent_app){
        super();
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        Label height_label = new Label("Height:");
        this.add(height_label, 0, 1);

        TextField height_input = new TextField();
        this.add(height_input, 1, 1);

        Label width_label = new Label("Width:");
        this.add(width_label, 0, 2);

        TextField width_input = new TextField();
        this.add(width_input, 1, 2);

        Label jungle_ratio_label = new Label("Jungle ratio:");
        this.add(jungle_ratio_label, 0, 3);

        TextField jungle_ratio_input = new TextField();
        this.add(jungle_ratio_input, 1, 3);

        Label initial_energy_label = new Label("Initial animals' energy:");
        this.add(initial_energy_label, 0, 4);

        TextField initial_energy_input = new TextField();
        this.add(initial_energy_input, 1, 4);

        Label plant_energy_label = new Label("Plant energy:");
        this.add(plant_energy_label, 0, 5);

        TextField plant_energy_input = new TextField();
        this.add(plant_energy_input, 1, 5);

        Button btn = new Button("Go");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        this.add(hbBtn, 1, 6);

        btn.setOnAction(e -> parent_app.renderMainStage());
    }
}
