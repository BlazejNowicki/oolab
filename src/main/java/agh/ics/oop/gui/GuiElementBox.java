package agh.ics.oop.gui;

import agh.ics.oop.IMapElement;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

    public class GuiElementBox extends VBox {
        public GuiElementBox(IMapElement object){
            super();
            try{
                Image image = new Image(new FileInputStream(object.getImagePath()));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                this.getChildren().add(imageView);
            }
            catch(FileNotFoundException e){
                System.out.println("Unable to read object's icon");
            }
        }
    }
