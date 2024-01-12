package org.example.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class WorldElementBox extends VBox {

    private final MapField mapField;

    private final Vector2d vector2d;

    private int amountOfAnimals = 0;

    private boolean grassPlaced = false;

    private Label label;

    private Label grassLabel;

    public WorldElementBox(MapField mapField, Vector2d vector2d) {
        this.mapField = mapField;
        this.vector2d = vector2d;
        this.setStyle("-fx_background-color: green;");
        this.label = new Label("");
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(label);
    }


    public void setCustomBackground(){
        grassPlaced=mapField.isGrass();
    }

    public void update() {
        this.amountOfAnimals = mapField.animals.size();
        this.getChildren().removeAll(label, grassLabel);
        setCustomBackground();
        if (this.amountOfAnimals != 0) {
            this.label = new Label(amountOfAnimals + "");
        } else {
            this.label = new Label();
        }
        if(isGrassPlaced()){
            this.grassLabel=new Label("*");
        }else{
            this.grassLabel=new Label("");
        }

        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx_background-color: green;");
        this.getChildren().addAll(label, grassLabel);
        this.setStyle("-fx_background-color: green;");
    }

    public Vector2d getPosition(){
        return vector2d;
    }

    public boolean isGrassPlaced(){
        return grassPlaced;
    }
}
