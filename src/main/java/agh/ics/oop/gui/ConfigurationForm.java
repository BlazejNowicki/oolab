package agh.ics.oop.gui;

import agh.ics.oop.MapConfiguration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ConfigurationForm extends GridPane {
    public ConfigurationForm(App parent_app){
        super();
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        Label height_label = new Label("Height:");
        this.add(height_label, 0, 1);

        TextField height_input = new TextField("10");
        this.add(height_input, 1, 1);

        Label width_label = new Label("Width:");
        this.add(width_label, 0, 2);

        TextField width_input = new TextField("10");
        this.add(width_input, 1, 2);

        Label jungle_ratio_label = new Label("Jungle ratio:");
        this.add(jungle_ratio_label, 0, 3);

        TextField jungle_ratio_input = new TextField("0.5");
        this.add(jungle_ratio_input, 1, 3);

        Label initial_energy_label = new Label("Initial animals' energy:");
        this.add(initial_energy_label, 0, 4);

        TextField initial_energy_input = new TextField("50");
        this.add(initial_energy_input, 1, 4);

        Label plant_energy_label = new Label("Plant energy:");
        this.add(plant_energy_label, 0, 5);

        TextField plant_energy_input = new TextField("30");
        this.add(plant_energy_input, 1, 5);

        Label move_energy_label = new Label("Move energy:");
        this.add(move_energy_label, 0, 6);

        TextField move_energy_input = new TextField("2");
        this.add(move_energy_input, 1, 6);

        Label number_of_animals_label = new Label("Initial number of animals:");
        this.add(number_of_animals_label, 0,7);

        TextField number_of_animals_input = new TextField("10");
        this.add(number_of_animals_input, 1, 7);

        Label delay_label = new Label("Delay: [ms]");
        this.add(delay_label, 0, 8);

        TextField delay_input = new TextField("500");
        this.add(delay_input, 1, 8);

        CheckBox magic_left = new CheckBox("Magic unbounded map");
        CheckBox magic_right = new CheckBox("Magic bounded map");
        this.add(magic_left, 0, 9);
        this.add(magic_right, 0, 10);

        Button btn = new Button("Go");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        this.add(hbBtn, 1, 11);

        btn.setOnAction(m -> {
            try{
                int width = Integer.parseInt(width_input.getText());
                int height = Integer.parseInt(height_input.getText());
                double jungle_ratio = Double.parseDouble(jungle_ratio_input.getText());
                int initial_energy = Integer.parseInt(initial_energy_input.getText());
                int plant_energy = Integer.parseInt(plant_energy_input.getText());
                int delay = Integer.parseInt(delay_input.getText());
                int move_energy = Integer.parseInt(move_energy_input.getText());
                int number_of_animals = Integer.parseInt(number_of_animals_input.getText());
                parent_app.renderMainStage(
                        new MapConfiguration(width, height, jungle_ratio, initial_energy, plant_energy, move_energy, number_of_animals, Math.max(30,delay)),
                        magic_left.isSelected(),
                        magic_right.isSelected());
            } catch(NumberFormatException e){
                System.out.println(e);
            }
        });
    }
}
