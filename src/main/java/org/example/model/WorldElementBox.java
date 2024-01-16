package org.example.model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.presenter.SimulationPresenter;
import javafx.scene.shape.Rectangle;

import java.util.Collections;

public class WorldElementBox extends VBox {

    private final MapField mapField;

    private final Vector2d vector2d;

    private int amountOfAnimals = 0;

    private boolean grassPlaced = false;
    SimulationPresenter simulationPresenter;

    private Label label;

    private Rectangle animalRepresentation;

    private final int energyClassifier;


    public WorldElementBox(MapField mapField, Vector2d vector2d, SimulationPresenter simulationPresenter, int energyClassifier) {
        this.mapField = mapField;
        this.vector2d = vector2d;
        this.simulationPresenter = simulationPresenter;
        this.label = new Label("");
        this.animalRepresentation =new Rectangle();
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(animalRepresentation);
        this.energyClassifier=energyClassifier;

        this.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY){
                handleMouseClicked();
            }
        });
    }


    public void setCustomBackground(){
        grassPlaced=mapField.isGrass();
    }

    public void update() {
        this.amountOfAnimals = mapField.animals.size();
        this.getChildren().removeAll(animalRepresentation);
        setCustomBackground();
        if (this.amountOfAnimals != 0) {
            Collections.sort(mapField.animals);
            int animalEnergy = mapField.animals.get(mapField.animals.size()-1).getEnergy();
            this.label = new Label(amountOfAnimals + "");
            if(animalEnergy < energyClassifier){
                animalRepresentation= new Rectangle(10, 10, Color.rgb(0,50,200));
            }
            else if(animalEnergy < 2*energyClassifier){
                animalRepresentation=new Rectangle(10, 10, Color.rgb(150,150,0));
            }
            else{
                animalRepresentation=new Rectangle(10, 10, Color.rgb(200,50,0));
            }

            this.setAlignment(Pos.CENTER);
            this.getChildren().addAll(animalRepresentation);
//            System.out.println("Weszlo");
        } else {
            this.animalRepresentation =new Rectangle();
            this.getChildren().addAll(animalRepresentation);
            this.setAlignment(Pos.CENTER);
        }
    }

    public Vector2d getPosition(){
        return vector2d;
    }

    public boolean isGrassPlaced(){
        return grassPlaced;
    }

    public boolean isPreferred(){
        return mapField.preferred;
    }

    private void handleMouseClicked(){
        if (!simulationPresenter.getSimulation().isPause) return;
        if(mapField.animals.isEmpty()) return;
        simulationPresenter.setTracking(true);
        simulationPresenter.animalStatsShow.setVisible(true);
        Collections.sort(mapField.animals);
        Animal animal = mapField.animals.get(mapField.animals.size()-1);
        simulationPresenter.setAnimalTracking(animal);
        simulationPresenter.animalStatistics();
        simulationPresenter.markTrackedAnimal(this);
    }
}
