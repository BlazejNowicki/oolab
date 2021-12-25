package agh.ics.oop.gui;

import agh.ics.oop.IMapElement;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

    public class GuiMapElement extends VBox {
        public GuiMapElement(IMapElement object){
            super();
            try{
                Image image = new Image(new FileInputStream(object.getImagePath()));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                this.getChildren().add(imageView);
                this.setAlignment(Pos.CENTER);
                GridPane.setHalignment(this, HPos.CENTER);
            }
            catch(FileNotFoundException e){
                System.out.println("Unable to read object's icon");
            }
        }
    }
