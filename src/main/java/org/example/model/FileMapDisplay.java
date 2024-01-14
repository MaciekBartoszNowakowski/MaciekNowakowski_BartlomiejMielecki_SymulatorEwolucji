package org.example.model;

import java.io.FileWriter;
import com.opencsv.CSVWriter;

public class FileMapDisplay implements MapChangeListener {


    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Statistics stats=worldMap.getStatistics();
        String fileName = "EvolutonSimulationData.csv";
        try (FileWriter fileWrite = new FileWriter(fileName, true)) {
            fileWrite.write("Day of Simulation: " + worldMap.getWorldAge() + "\n");
            fileWrite.write("Amount of living animals: "+stats.getTotalAnimals() + "\n");
            fileWrite.write("Amount of grass: "+stats.getTotalGrass() + "\n");
            fileWrite.write("Amount of free fields: "+stats.getFreeFields() + "\n");
            fileWrite.write("Avarage energy: "+stats.getAverageEnergy() + "\n");
            fileWrite.write("Avarage lifespan: "+stats.getAverageLifespan() + "\n");
            fileWrite.write("Avarage children amount: "+stats.getAverageChildren() + "\n");
            fileWrite.write("Amount of dead animals: "+stats.getDeadAnimals().size() + "\n");
            fileWrite.write("All genomes and popularity of them"+ "\n");
            for (String currGenotype : stats.returnGenotypesList(stats.getGenotypeCounts().size())){
                fileWrite.write(currGenotype + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
