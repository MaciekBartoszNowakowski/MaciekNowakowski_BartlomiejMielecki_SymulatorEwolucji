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

    public WorldElementBox(MapField mapField, Vector2d vector2d) {
        this.mapField = mapField;
        this.vector2d = vector2d;
        this.label = new Label("");
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(label);
    }


    public void setCustomBackground(){
        grassPlaced=mapField.isGrass();
    }

    public void update() {
        this.amountOfAnimals = mapField.animals.size();
        this.getChildren().removeAll(label);
        setCustomBackground();
        if (this.amountOfAnimals != 0) {
            this.label = new Label(amountOfAnimals + "");
        } else {
            this.label = new Label();
        }
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(label);
    }

    public Vector2d getPosition(){
        return vector2d;
    }

    public boolean isGrassPlaced(){
        return grassPlaced;
    }
}
