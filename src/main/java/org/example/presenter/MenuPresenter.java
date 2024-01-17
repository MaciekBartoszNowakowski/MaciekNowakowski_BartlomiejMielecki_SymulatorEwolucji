package org.example.presenter;

import com.opencsv.CSVWriter;
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
                    , partitionEnergy.getValue(), mutationSystem, grassAmount.getValue());
        } else {
            worldMap = new HellGate(height.getValue(), width.getValue(), animalEnergy.getValue(), animalsAmount.getValue(),
                    codeLength.getValue(), dailyGrowth.getValue(), grassEnergy.getValue(), healthyEnergyAmount.getValue()
                    , partitionEnergy.getValue(), mutationSystem, grassAmount.getValue());

        }


        int widthBox=600/width.getValue();
        int heightBox=600/height.getValue();

        if(raportCheckobox.isSelected()){
            FileMapDisplay fileMapDisplay = new FileMapDisplay();
            try {
                File file = new File("EvolutonSimulationData.csv");
                FileWriter outputFile = new FileWriter(file);
                CSVWriter writer = new CSVWriter(outputFile,';', CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
                String[] header = {"Day","AmountOfAnimals","AmountofGrass","AvarageAmountOfChidren","MostPopularGenotype","AvarageAge", "AvarageAmountOfEnergy"};
                System.out.println(header[0]);
                writer.writeNext(header);
                outputFile.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            worldMap.register(fileMapDisplay);
        }


        Stage stage = new Stage();
        SimulationApp simulationApp = new SimulationApp(stage, worldMap,widthBox,heightBox);

    }

    public void updateSettingsBox(){
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
            worldType.setValue(line);
//          height
            line=bufferedReader.readLine();
            height.getValueFactory().setValue(Integer.valueOf(line));
//          width
            line=bufferedReader.readLine();
            width.getValueFactory().setValue(Integer.valueOf(line));
//          grassAmount
            line=bufferedReader.readLine();
            grassAmount.getValueFactory().setValue(Integer.valueOf(line));
//            grassEnergy
            line=bufferedReader.readLine();
            grassEnergy.getValueFactory().setValue(Integer.valueOf(line));
//            dailyGrowth
            line=bufferedReader.readLine();
            dailyGrowth.getValueFactory().setValue(Integer.valueOf(line));
//            mutationTypes
            line=bufferedReader.readLine();
            mutationTypes.setValue(line);
//            animalsAmount
            line=bufferedReader.readLine();
            animalsAmount.getValueFactory().setValue(Integer.valueOf(line));
//            animalEnergy
            line=bufferedReader.readLine();
            animalEnergy.getValueFactory().setValue(Integer.valueOf(line));
//            healthyEnergyAmount
            line=bufferedReader.readLine();
            healthyEnergyAmount.getValueFactory().setValue(Integer.valueOf(line));
//            partitionEnergy
            line=bufferedReader.readLine();
            partitionEnergy.getValueFactory().setValue(Double.valueOf(line));
//          miniMutation
            line=bufferedReader.readLine();
            miniMutation.getValueFactory().setValue(Integer.valueOf(line));
//            maksiMutation
            line=bufferedReader.readLine();
            maksiMutation.getValueFactory().setValue(Integer.valueOf(line));
//          codeLength
            line=bufferedReader.readLine();
            codeLength.getValueFactory().setValue(Integer.valueOf(line));

        }catch (IOException e) {
            e.printStackTrace();
        }


    }
}
