package org.example.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import org.example.SimulationApp;
import org.example.model.*;

import java.io.*;
import java.util.Arrays;

public class MenuPresenter {

    public CheckBox raportCheckobox;
    public TextField newName;
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

    @FXML ComboBox<String> settings;

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

        int widthBox=600/width.getValue();
        int heightBox=600/height.getValue();

        if(raportCheckobox.isSelected()){
            FileMapDisplay fileMapDisplay=new FileMapDisplay();
            worldMap.register(fileMapDisplay);
        }


        Stage stage = new Stage();
        SimulationApp simulationApp = new SimulationApp(stage, worldMap,widthBox,heightBox);

    }

    public void updateSettingsBox(){
        System.out.println("BajoJajo");
        String directoryPath ="maps";
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()){
            File[] files = directory.listFiles();
            if (files !=null){
                ObservableList<String> fileNames = FXCollections.observableArrayList();
                Arrays.stream(files)
                        .filter(File::isFile)
                        .forEach(file -> fileNames.add(file.getName()));

                settings.setItems(fileNames);
                for(String name : fileNames){
                    System.out.println(name);
                }
            }
        }


    }

    public void onSaveClicked(ActionEvent actionEvent) {
        String name= newName.getText();
        String fileName= "maps/"+name+".txt";
        try (FileWriter fileWriter = new FileWriter(fileName,true)){
            fileWriter.write(worldType.getValue()+ "\n");
            fileWriter.write(height.getValue()+ "\n");
            fileWriter.write(width.getValue()+ "\n");
            fileWriter.write(grassAmount.getValue()+ "\n");
            fileWriter.write(grassEnergy.getValue()+ "\n");
            fileWriter.write(dailyGrowth.getValue()+ "\n");
            fileWriter.write(mutationTypes.getValue()+ "\n");
            fileWriter.write(animalsAmount.getValue()+ "\n");
            fileWriter.write(animalEnergy.getValue()+ "\n");
            fileWriter.write(healthyEnergyAmount.getValue()+ "\n");
            fileWriter.write(partitionEnergy.getValue()+ "\n");
            fileWriter.write(miniMutation.getValue()+ "\n");
            fileWriter.write(maksiMutation.getValue()+ "\n");
            fileWriter.write(codeLength.getValue()+ "\n");


        } catch (IOException e) {
            e.printStackTrace();
        }

        updateSettingsBox();

    }

    public void onLoadClicked(ActionEvent actionEvent) {
        String filePath="maps/"+settings.getValue();

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line;
//            Map
            line=bufferedReader.readLine();
            System.out.println(line);
            worldType.setValue(line);
//          height
            line=bufferedReader.readLine();
            System.out.println(line);
            height.getValueFactory().setValue(Integer.valueOf(line));
//          width
            line=bufferedReader.readLine();
            System.out.println(line);
            width.getValueFactory().setValue(Integer.valueOf(line));
//          grassAmount
            line=bufferedReader.readLine();
            System.out.println(line);
            grassAmount.getValueFactory().setValue(Integer.valueOf(line));
//            grassEnergy
            line=bufferedReader.readLine();
            System.out.println(line);
            grassEnergy.getValueFactory().setValue(Integer.valueOf(line));
//            dailyGrowth
            line=bufferedReader.readLine();
            System.out.println(line);
            dailyGrowth.getValueFactory().setValue(Integer.valueOf(line));
//            mutationTypes
            line=bufferedReader.readLine();
            System.out.println(line);
            mutationTypes.setValue(line);
//            animalsAmount
            line=bufferedReader.readLine();
            System.out.println(line);
            animalsAmount.getValueFactory().setValue(Integer.valueOf(line));
//            animalEnergy
            line=bufferedReader.readLine();
            System.out.println(line);
            animalEnergy.getValueFactory().setValue(Integer.valueOf(line));
//            healthyEnergyAmount
            line=bufferedReader.readLine();
            System.out.println(line);
            healthyEnergyAmount.getValueFactory().setValue(Integer.valueOf(line));
//            partitionEnergy
            line=bufferedReader.readLine();
            System.out.println(line);
            partitionEnergy.getValueFactory().setValue(Double.valueOf(line));
//          miniMutation
            line=bufferedReader.readLine();
            System.out.println(line);
            miniMutation.getValueFactory().setValue(Integer.valueOf(line));
//            maksiMutation
            line=bufferedReader.readLine();
            System.out.println(line);
            maksiMutation.getValueFactory().setValue(Integer.valueOf(line));
//          codeLength
            line=bufferedReader.readLine();
            System.out.println(line);
            codeLength.getValueFactory().setValue(Integer.valueOf(line));

        }catch (IOException e) {
            e.printStackTrace();
        }


    }
}
