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

    private static final double CELL_WIDTH = 20;
    private static final double CELL_HEIGHT = 20;
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
        System.out.println("columns: " +columns +" "+"Lines "+lines);


        for(WorldElementBox currBox : fieldBoxes){
            currBox.update();
            if (currBox.isGrassPlaced()){
                currBox.setBackground(new Background(new BackgroundFill(Color.rgb(0,255,0), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else{
                currBox.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,255), CornerRadii.EMPTY, Insets.EMPTY)));

            }
            currBox.setBorder(new Border(new BorderStroke(Color.valueOf("#9E9E9E"),
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    BorderWidths.DEFAULT)));
            Vector2d vector2d = currBox.getPosition();
            mapGrid.add(currBox, vector2d.getX(), vector2d.getY());
            GridPane.setHalignment(currBox, HPos.CENTER);
        }

        for (int i =0;i<columns;i++) mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        for (int i =0;i<lines;i++) mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));


//        System.out.println("Odswiezenie");

    }

    private void updateStatistics(){
        Statistics statistics = worldMap.getStatistics();
        ObservableList<String> genotypes = FXCollections.observableArrayList();
        genotypes.addAll(statistics.returnGenotypesList(10));
        mostCommonGenotypes.setItems(genotypes);
        ObservableList<String> statsHeadlines = FXCollections.observableArrayList();
        statsHeadlines.addAll("Animals amount", "Grass amount", "Free fields amount", "Average energy", "Average lifespan", "Average offsprings amount");
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
            fieldBoxes.add(new WorldElementBox(value, key, this));
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

        List<Vector2d> mostCommonGenotype = new ArrayList<>();
        String genotype = worldMap.getStatistics().getTopGenotypes(1).get(0).getKey();
        for(Animal animal : worldMap.getAnimals()){
            if(Objects.equals(animal.getGeneticCode(), genotype)){
                mostCommonGenotype.add(animal.getPosition());
            }
        }


        for(WorldElementBox currBox : fieldBoxes){
            if(currBox.isPreferred()) currBox.setBackground(new Background(new BackgroundFill(Color.rgb(0,155,0), CornerRadii.EMPTY, Insets.EMPTY)));
            if (currBox.isGrassPlaced()){currBox.setBackground(new Background(new BackgroundFill(Color.rgb(0,255,0), CornerRadii.EMPTY, Insets.EMPTY)));}
            if (mostCommonGenotype.contains(currBox.getPosition()))currBox.setBackground(new Background(new BackgroundFill(Color.rgb(150,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        }

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
}
