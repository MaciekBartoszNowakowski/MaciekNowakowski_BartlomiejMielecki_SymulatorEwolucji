package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.model.WorldMap;
import org.example.presenter.SimulationPresenter;

public class SimulationApp{


    public SimulationApp(Stage primaryStage, WorldMap worldMap,int width, int height) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("mapVisualization.fxml"));
        BorderPane viewRoot = loader.load();


        SimulationPresenter simulationPresenter = loader.getController();
        simulationPresenter.setWorldMap(worldMap);
        worldMap.register(simulationPresenter);

        simulationPresenter.setCELL_WIDTH(width);
        simulationPresenter.setCELL_HEIGHT(height);

        configureStage(primaryStage,viewRoot);
        primaryStage.show();
        simulationPresenter.onStartedNewSimulation();
    }


    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("mapVisualization");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

}
