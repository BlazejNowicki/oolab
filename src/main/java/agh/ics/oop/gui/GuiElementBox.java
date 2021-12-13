package agh.ics.oop.gui;

import agh.ics.oop.AbstractMapObject;
import agh.ics.oop.Grass;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox extends VBox {
    public GuiElementBox(AbstractMapObject object){
        super();
        try{
            Image image = new Image(new FileInputStream(object.getResourcePath()));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            this.getChildren().add(imageView);
        }
        catch(FileNotFoundException e){
            System.out.println("Unable to read object's icon");
        }
        Label label;
        if(object instanceof Grass){
            label = new Label("Trawa");
        } else {
            label = new Label(object.getPosition().toString());
        }
        this.getChildren().add(label);
    }
}
