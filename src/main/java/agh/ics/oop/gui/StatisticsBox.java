package agh.ics.oop.gui;

import agh.ics.oop.IMap;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.StatHistory;
import agh.ics.oop.Statistics;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class StatisticsBox extends VBox {
    private final Label animal_counter = new Label();
    private final Label plant_counter = new Label();
    private final Label average_energy_counter = new Label();
    private final Label average_lifespan_counter = new Label();
    private final Label average_child_counter = new Label();
    private final Label dominant_counter = new Label();
    private final XYChart.Series animal_series = new XYChart.Series();
    private final XYChart.Series plant_series = new XYChart.Series();
    private final XYChart.Series lifespan_series = new XYChart.Series();
    private final XYChart.Series energy_series = new XYChart.Series();
    private final XYChart.Series child_series = new XYChart.Series();

    private final StatHistory history = new StatHistory();


    public StatisticsBox(){
        super();
        this.setPadding(new Insets(10, 10, 10, 10));


        GridPane grid = new GridPane();
        this.setupGrid(grid);
        this.getChildren().add(grid);


        this.animalsPlantsChart();
        this.avgLifespanChart();
        this.avgEnergyChart();
        this.avgChildrenChart();

        this.setupDominant();
    }

    private void setupDominant(){
        VBox container = new VBox();
        this.getChildren().add(container);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10,10,10,10));
        container.getChildren().add(new Label("Dominant genome"));
        container.setStyle("-fx-border-color: black;");
        container.getChildren().add(dominant_counter);

        dominant_counter.setAlignment(Pos.CENTER);
        dominant_counter.setPadding(new Insets(10,10,10,10));
    }

    private void animalsPlantsChart(){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day");
        final LineChart<Number,Number> chart = new LineChart<>(xAxis,yAxis);
        chart.setCreateSymbols(false);

        animal_series.setName("Number of animals");
        plant_series.setName("Number of plants");

        chart.getData().add(animal_series);
        chart.getData().add(plant_series);

        chart.setMaxWidth(350);
        chart.setMaxHeight(400);
        this.getChildren().add(chart);
    }

    private void avgLifespanChart(){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day");
        final LineChart<Number,Number> chart = new LineChart<>(xAxis,yAxis);
        chart.setCreateSymbols(false);
//        lifespan_series.setName("Average lifespan");
        yAxis.setLabel("Avg lifespan");

        lifespan_series.setName("Average lifespan");
        chart.getData().add(lifespan_series);

        chart.setMaxWidth(350);
        chart.setMaxHeight(200);
        this.getChildren().add(chart);
    }

    private void avgEnergyChart(){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day");
        final LineChart<Number,Number> chart = new LineChart<>(xAxis,yAxis);
        chart.setCreateSymbols(false);

        energy_series.setName("Average energy");
        yAxis.setLabel("Avg energy");

        chart.getData().add(energy_series);

        chart.setMaxWidth(350);
        chart.setMaxHeight(200);
        this.getChildren().add(chart);
    }

    private void avgChildrenChart(){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day");
        final LineChart<Number,Number> chart = new LineChart<>(xAxis,yAxis);
        chart.setCreateSymbols(false);

        child_series.setName("Average number of children");
        yAxis.setLabel("Avg children");


        chart.getData().add(child_series);

        chart.setMaxWidth(350);
        chart.setMaxHeight(200);
        this.getChildren().add(chart);
    }

    private void setupGrid(GridPane grid){
        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.CENTER);

        Label animal_number_label = new Label("Number of animals");
        grid.add(animal_number_label, 0,0);
        GridPane.setMargin(animal_number_label, new Insets(3,3,3,3));
        grid.add(this.animal_counter, 1,0);
        GridPane.setMargin(animal_counter, new Insets(3,3,3,3));

        Label plant_number_label = new Label("Number of plants");
        grid.add(plant_number_label, 0,1);
        GridPane.setMargin(plant_number_label, new Insets(3,3,3,3));
        grid.add(this.plant_counter, 1,1);
        GridPane.setMargin(plant_counter, new Insets(3,3,3,3));

        Label average_energy_label = new Label("Average animals' energy");
        grid.add(average_energy_label, 0,2);
        GridPane.setMargin(average_energy_label, new Insets(3,3,3,3));
        grid.add(this.average_energy_counter, 1,2);
        GridPane.setMargin(average_energy_counter, new Insets(3,3,3,3));

        Label average_lifespan_label = new Label("Average animals' lifespan");
        grid.add(average_lifespan_label, 0,3);
        GridPane.setMargin(average_lifespan_label, new Insets(3,3,3,3));
        grid.add(this.average_lifespan_counter, 1,3);
        GridPane.setMargin(average_lifespan_counter, new Insets(3,3,3,3));

        Label average_child_label = new Label("Average number of children");
        grid.add(average_child_label, 0,4);
        GridPane.setMargin(average_child_label, new Insets(3,3,3,3));
        grid.add(this.average_child_counter, 1,4);
        GridPane.setMargin(average_child_counter, new Insets(3,3,3,3));
    }

    public void update(SimulationEngine engine){
        IMap map = engine.getMap();
        Statistics stat = map.getStatistics();
        int day = map.getDate();
        this.animal_counter.setText(Integer.toString(stat.animals()));
        this.plant_counter.setText(Integer.toString(stat.plants()));

        this.average_energy_counter.setText(String.format("%.2f", stat.avg_energy()));
        this.average_lifespan_counter.setText(String.format("%.2f", stat.avg_lifespan()));
        this.average_child_counter.setText(String.format("%.2f", stat.avg_children()));
        this.dominant_counter.setText(stat.dominant().toString());

        this.animal_series.getData().add(new XYChart.Data(day, stat.animals()));
        this.plant_series.getData().add(new XYChart.Data(day, stat.plants()));
        if (!Double.isNaN(stat.avg_lifespan())) {
            this.lifespan_series.getData().add(new XYChart.Data(day, stat.avg_lifespan()));
        }
        this.energy_series.getData().add(new XYChart.Data(day, stat.avg_energy()));
        this.child_series.getData().add(new XYChart.Data(day, stat.avg_children()));

        this.history.addRecord(
                stat.animals(),
                stat.plants(),
                stat.avg_energy(),
                stat.avg_lifespan(),
                stat.avg_children()
        );
    }

    public void saveToFile(String path){
        this.history.saveToFile(path);
    }

}
