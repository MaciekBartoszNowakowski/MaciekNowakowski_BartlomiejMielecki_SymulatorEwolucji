package org.example.presenter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.Simulation;
import org.example.SimulationEngine;
import org.example.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SimulationPresenter implements MapChangeListener {

    private int CELL_WIDTH = 20;
    private int CELL_HEIGHT = 20;
    public ListView<String> mapStatsHeadlines;
    public ListView<String> mostCommonGenotypes;
    public ListView<String> animalStats;
    public ListView<String> animalStatsHeadlines;

    boolean tracking = false;

    private SimulationEngine simulationEngine;

    private Simulation simulation;

    public WorldMap worldMap;

    @FXML
    private GridPane mapGrid;

    public HBox animalStatsShow;

    public ListView<String> mapStats;
    private List<WorldElementBox> fieldBoxes = new ArrayList<>();

    private AnimalTracking animalTracking = null;
    private WorldElementBox markedBox = null;
    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    private void clearGrid(){
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public void drawMap(String message){
        clearGrid();

//        int maxX= worldMap.getWidth()-1;
//        int maxY = worldMap.getHeight()-1;
//        int minX = 0;
//        int minY = 0;
        int columns = worldMap.getWidth();
        int lines = worldMap.getHeight();


        for(WorldElementBox currBox : fieldBoxes){
            currBox.update();
            Vector2d vector2d = currBox.getPosition();
            Animal animalTracked = null;
            if (tracking && !animalTracking.getIsDead()){
                animalTracked = animalTracking.getAnimal();
            }
            if (currBox.isGrassPlaced()){
                colorGrass(currBox);
            } else if (animalTracked != null && animalTracked.getPosition().equals(vector2d)) {
                markTrackedAnimal(currBox);
            } else{
                colorEmptyField(currBox);
            }
            currBox.setBorder(new Border(new BorderStroke(Color.valueOf("#9E9E9E"),
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    BorderWidths.DEFAULT)));
            mapGrid.add(currBox, vector2d.getX(), vector2d.getY());
            GridPane.setHalignment(currBox, HPos.CENTER);
        }

        for (int i =0;i<columns;i++) mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        for (int i =0;i<lines;i++) mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));



    }

    private void updateStatistics(){
        Statistics statistics = worldMap.getStatistics();
        ObservableList<String> genotypes = FXCollections.observableArrayList();
        genotypes.addAll(statistics.returnGenotypesList(10));
        mostCommonGenotypes.setItems(genotypes);
        ObservableList<String> statsHeadlines = FXCollections.observableArrayList();
        statsHeadlines.addAll("Animals amount", "Grass amount", "Free fields amount", "Average energy", "Average lifespan", "Average children amount");
        mapStatsHeadlines.setItems(statsHeadlines);
        ObservableList<String> stats = FXCollections.observableArrayList();
        stats.addAll(statistics.returnStatisticsList());
        mapStats.setItems(stats);
    }

    public void animalStatistics(){
        if(!tracking) {
            animalStatsShow.setVisible(false);
            return;
        }
        ObservableList<String> animalStatsHeadlines = FXCollections.observableArrayList();
        animalStatsHeadlines.addAll("Genotype", "Current genome", "Grass eaten", "Children amount", "Offsprings amount", "Age", "Death day");
        this.animalStatsHeadlines.setItems(animalStatsHeadlines);
        ObservableList<String> animalStats = FXCollections.observableArrayList();
        animalStats.addAll(animalTracking.returnAnimalStatistics());
        this.animalStats.setItems(animalStats);
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            animalStatistics();
            updateStatistics();
            drawMap(message);
        });
    }



    public void onStartedNewSimulation() throws InterruptedException {
        simulation= new Simulation(worldMap);
        List<Simulation> simulations = new ArrayList<>();


        worldMap.getFields().forEach((key, value)->{
            fieldBoxes.add(new WorldElementBox(value, key, this, worldMap.getEnergyToReproduce(),CELL_WIDTH,CELL_HEIGHT));
            });

        simulations.add(simulation);
        simulationEngine= new SimulationEngine(simulations);
        simulationEngine.runAsync();
    }


    public void onPlayClicked(ActionEvent actionEvent) {
        simulation.setPause(false);
    }

    public void onStopTrackingClicked(ActionEvent actionEvent) {
        tracking = false;
    }


    public void onPauseClicked(ActionEvent actionEvent) throws InterruptedException {
        simulation.setPause(true);

        for(WorldElementBox currBox : fieldBoxes){
            if(currBox.isPreferred()) colorPreferredField(currBox);
            if (currBox.isGrassPlaced()) colorGrass(currBox);
            if (mostCommonGenotypePositions().contains(currBox.getPosition())) colorMostCommonGenotype(currBox);
            if (tracking && !animalTracking.getIsDead() & markedBox != null) markedBox.setBackground(new Background(new BackgroundFill(Color.rgb(148,0,211), CornerRadii.EMPTY, Insets.EMPTY)));
        }

    }

    private List<Vector2d> mostCommonGenotypePositions(){
        List<Vector2d> mostCommonGenotype = new ArrayList<>();
        if(!worldMap.getAnimals().isEmpty()){
            String genotype = worldMap.getStatistics().getTopGenotypes(1).get(0).getKey();
            for(Animal animal : worldMap.getAnimals()){
                if(Objects.equals(animal.getGeneticCode(), genotype)){
                    mostCommonGenotype.add(animal.getPosition());
                }
            }
        }
        return mostCommonGenotype;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setTracking(boolean value){
        tracking = value;
    }

    public void setAnimalTracking(Animal animal) {
        this.animalTracking = new AnimalTracking(animal, worldMap);
    }

    private void colorGrass(WorldElementBox currBox){
        currBox.setBackground(new Background(new BackgroundFill(Color.rgb(0,255,0), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void colorPreferredField(WorldElementBox currBox){
        currBox.setBackground(new Background(new BackgroundFill(Color.rgb(0,155,0), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void colorEmptyField(WorldElementBox currBox){
        currBox.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,255), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void colorMostCommonGenotype(WorldElementBox currBox){
        currBox.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,0), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void markTrackedAnimal(WorldElementBox currBox) {
        if (markedBox != null){
            if (mostCommonGenotypePositions().contains(markedBox.getPosition())) colorMostCommonGenotype(markedBox);
            else if (markedBox.isPreferred()) colorPreferredField(markedBox);
            else if (markedBox.isGrassPlaced()) colorGrass(markedBox);
            else colorEmptyField(markedBox);
        }
        currBox.setBackground(new Background(new BackgroundFill(Color.rgb(148,0,211), CornerRadii.EMPTY, Insets.EMPTY)));
        markedBox = currBox;
    }


    public void setCELL_WIDTH(int CELL_WIDTH) {
        this.CELL_WIDTH = CELL_WIDTH;
    }

    public void setCELL_HEIGHT(int CELL_HEIGHT) {
        this.CELL_HEIGHT = CELL_HEIGHT;
    }
}
