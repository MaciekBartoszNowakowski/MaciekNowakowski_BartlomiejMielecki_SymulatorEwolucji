package org.example.presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.example.Simulation;
import org.example.SimulationEngine;
import org.example.model.MapChangeListener;
import org.example.model.WorldElementBox;
import org.example.model.WorldMap;
import org.example.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {

    private static final double CELL_WIDTH = 20;
    private static final double CELL_HEIGHT = 20;

    private WorldMap worldMap;

    @FXML
    private GridPane mapGrid;

    private List<WorldElementBox> fieldBoxes= new ArrayList<>();

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

//        Label osField = new Label("y/x");
//        mapGrid.add(osField, 0 ,0);
//        for(int i=0; i<=columns;i++){
//            Label posField = new Label(""+(i));
//            mapGrid.add(posField,i+1,0);
//            GridPane.setHalignment(posField, HPos.CENTER);
//        }
//        for(int i=0; i<=lines;i++){
//            Label posField = new Label(""+(i));
//            mapGrid.add(posField,0,i+1);
//            GridPane.setHalignment(posField, HPos.CENTER);
//        }








        for (int i =0;i<columns;i++) mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        for (int i =0;i<lines;i++) mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));


        for(WorldElementBox currBox : fieldBoxes){
            currBox.update();
            if (currBox.isGrassPlaced()){
                currBox.setStyle("-fx_background-color: green;");
            }
            else{
                currBox.setStyle("-fx_background-color: white;");
            }
            Vector2d vector2d = currBox.getPosition();
            mapGrid.add(currBox, vector2d.getX(), vector2d.getY());
            GridPane.setHalignment(currBox, HPos.CENTER);
        }



//        System.out.println("Odswiezenie");

    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap(message);
        });
    }



    public void onStartedNewSimulation() throws InterruptedException {
        Simulation simulation= new Simulation(worldMap);
        List<Simulation> simulations = new ArrayList<>();


        worldMap.getFields().forEach((key, value)->{
            fieldBoxes.add(new WorldElementBox(value, key));
            });

        simulations.add(simulation);
        SimulationEngine simulationEngine= new SimulationEngine(simulations);
        simulationEngine.runAsync();
    }





}
