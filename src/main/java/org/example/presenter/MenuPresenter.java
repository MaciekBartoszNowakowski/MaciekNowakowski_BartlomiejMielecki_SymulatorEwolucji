package org.example.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import org.example.SimulationApp;
import org.example.model.*;

public class MenuPresenter {

    public CheckBox raportCheckobox;
    @FXML
    private Spinner<Integer> codeLength;
    @FXML
    private Spinner<Integer> maksiMutation;
    @FXML
    private Spinner<Integer> miniMutation;
    @FXML
    private Spinner<Double> partitionEnergy;
    @FXML
    private Spinner<Integer> healthyEnergyAmount;
    @FXML
    private Spinner<Integer> animalEnergy;
    @FXML
    private Spinner<Integer> animalsAmount;
    @FXML
    private ComboBox<String> mutationTypes;
    @FXML
    private Spinner<Integer> dailyGrowth;
    @FXML
    private Spinner<Integer> grassEnergy;
    @FXML
    private Spinner<Integer> grassAmount;
    @FXML
    private Spinner<Integer> width;
    @FXML
    private Spinner<Integer> height;
    @FXML
    private ComboBox<String> worldType;

    private MutationSystem mutationSystem;

    private WorldMap worldMap;


    public void onSimulationStartClicked() throws Exception {

        String mutationName = mutationTypes.getValue();
        if (mutationName.equals("Standard Mutation")) {
            mutationSystem = new StandardMutation(miniMutation.getValue(), maksiMutation.getValue());
        } else {
            mutationSystem = new SlightMutation(miniMutation.getValue(), maksiMutation.getValue());
        }
        String worldName = worldType.getValue();

        if (worldName.equals("Earth")) {
            worldMap = new Earth(height.getValue(), width.getValue(), animalEnergy.getValue(), animalsAmount.getValue(),
                    codeLength.getValue(), dailyGrowth.getValue(), grassEnergy.getValue(), healthyEnergyAmount.getValue()
                    , partitionEnergy.getValue(), mutationSystem);
        } else {
            worldMap = new HellGate(height.getValue(), width.getValue(), animalEnergy.getValue(), animalsAmount.getValue(),
                    codeLength.getValue(), dailyGrowth.getValue(), grassEnergy.getValue(), healthyEnergyAmount.getValue()
                    , partitionEnergy.getValue(), mutationSystem);

        }

        System.out.println(worldMap);

        if(raportCheckobox.isSelected()){
            FileMapDisplay fileMapDisplay=new FileMapDisplay();
            worldMap.register(fileMapDisplay);
        }


        Stage stage = new Stage();
        SimulationApp simulationApp = new SimulationApp(stage, worldMap);

    }
}
