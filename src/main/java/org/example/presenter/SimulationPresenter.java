package org.example.presenter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.Simulation;
import org.example.SimulationEngine;
import org.example.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SimulationPresenter implements MapChangeListener {

    private static final double CELL_WIDTH = 20;
    private static final double CELL_HEIGHT = 20;

    boolean tracking = false;

    private SimulationEngine simulationEngine;

    private Simulation simulation;

    public WorldMap worldMap;

    public Animal animalTracked = null;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Label totalAnimals;
    @FXML
    private Label totalGrasses;
    @FXML
    private Label freeFields;
    @FXML
    private Label genotype1;
    @FXML
    private Label genotype2;
    @FXML
    private Label genotype3;
    @FXML
    private Label genotype4;
    @FXML
    private Label averageLifespan;
    @FXML
    private Label averageChildren;
    @FXML
    private Label averageEnergy;

    public Label genotype;
    public Label currentGenome;
    public Label grassCount;
    public Label childrenAmount;
    public Label descendantsAmount;
    public Label animalAge;
    public Label deathDay;
    public HBox animalStats;

    public int animalDeathDay = 0;
    private List<WorldElementBox> fieldBoxes = new ArrayList<>();

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
        totalAnimals.setText(String.valueOf(statistics.getTotalAnimals()));
        totalGrasses.setText(String.valueOf(statistics.getTotalGrass()));
        freeFields.setText(String.valueOf(statistics.getFreeFields()));
        List<Map.Entry<String, Integer>> topGenotypes = statistics.getTopGenotypes(4);
        if(!topGenotypes.isEmpty()) genotype1.setText(topGenotypes.get(0).getKey() + ": " + topGenotypes.get(0).getValue());
        if(topGenotypes.size() > 1) genotype2.setText(topGenotypes.get(1).getKey() + ": " + topGenotypes.get(1).getValue());
        if(topGenotypes.size() > 2) genotype3.setText(topGenotypes.get(2).getKey() + ": " + topGenotypes.get(2).getValue());
        if(topGenotypes.size() > 3) genotype4.setText(topGenotypes.get(3).getKey() + ": " + topGenotypes.get(3).getValue());
        averageEnergy.setText(String.valueOf(statistics.getAverageEnergy()));
        averageLifespan.setText(String.valueOf(statistics.getAverageLifespan()));
        averageChildren.setText(String.valueOf(statistics.getAverageChildren()));
    }

    public void animalStatistics(){
        if(!tracking) {
            animalStats.setVisible(false);
            return;
        }
        genotype.setText(animalTracked.getGeneticCode());
        currentGenome.setText(String.valueOf(animalTracked.getCurrentGenome()));
        grassCount.setText(String.valueOf(animalTracked.getGrassCount()));
        childrenAmount.setText(String.valueOf(animalTracked.getChildrenAmount()));
        descendantsAmount.setText(String.valueOf(animalTracked.getOffspringsAmount()));
        animalAge.setText(String.valueOf(animalTracked.getAge()));
        if (animalDeathDay == 0){
            if (!worldMap.getAnimals().contains(animalTracked)) {
                animalDeathDay = worldMap.getWorldAge();
                deathDay.setText(String.valueOf(animalDeathDay));
            }
        }
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
}
