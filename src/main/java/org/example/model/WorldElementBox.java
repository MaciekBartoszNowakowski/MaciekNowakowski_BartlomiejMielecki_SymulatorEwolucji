package org.example.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import org.example.presenter.SimulationPresenter;

public class WorldElementBox extends VBox {

    private final MapField mapField;

    private final Vector2d vector2d;

    private int amountOfAnimals = 0;

    private boolean grassPlaced = false;
    SimulationPresenter simulationPresenter;

    private Label label;


    public WorldElementBox(MapField mapField, Vector2d vector2d, SimulationPresenter simulationPresenter) {
        this.mapField = mapField;
        this.vector2d = vector2d;
        this.simulationPresenter = simulationPresenter;
        this.label = new Label("");
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(label);

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
        this.getChildren().removeAll(label);
        setCustomBackground();
        if (this.amountOfAnimals != 0) {
            int animalEnergy = mapField.sortByStronger(mapField.animals).get(mapField.animals.size()-1).getEnergy();
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

    public boolean isPreferred(){
        return mapField.preferred;
    }

    private void handleMouseClicked(){
        if (!simulationPresenter.getSimulation().isPause) return;
        if(mapField.animals.isEmpty()) return;
        simulationPresenter.setTracking(true);
        simulationPresenter.animalStatsShow.setVisible(true);
        Animal animal = mapField.sortByStronger(mapField.animals).get(mapField.animals.size()-1);
        simulationPresenter.setAnimalTracking(animal);
        simulationPresenter.animalStatistics();
    }
}
