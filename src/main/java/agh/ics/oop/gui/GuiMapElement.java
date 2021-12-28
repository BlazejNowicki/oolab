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
import java.util.Map;

public class GuiMapElement extends VBox {
        public GuiMapElement(IMapElement object, Map<String, Image> cache){
            super();
            try{
                String path = object.getImagePath();
                Image image;
                if (cache.containsKey(path)){
                    image = cache.get(path);
                } else {
                    image = new Image(new FileInputStream(path));
                    cache.put(path, image);
                }
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);

                this.getChildren().add(imageView);
                this.setAlignment(Pos.CENTER);
                GridPane.setHalignment(this, HPos.CENTER);
            }
            catch(FileNotFoundException e){
                System.out.println("Unable to read object's icon");
            }
        }
    }
