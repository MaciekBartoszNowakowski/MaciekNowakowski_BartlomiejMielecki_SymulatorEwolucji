package org.example.model;

import java.io.File;
import java.io.FileWriter;
import com.opencsv.CSVWriter;

public class FileMapDisplay implements MapChangeListener {

    @Override
    public void mapChanged(WorldMap worldMap, String message) {

        Statistics stats=worldMap.getStatistics();
        try {
            File file = new File("EvolutonSimulationData.csv");
            FileWriter outputFile = new FileWriter(file,true);
            CSVWriter writer = new CSVWriter(outputFile,';', CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            String[] data={worldMap.getWorldAge()+"",stats.getTotalAnimals()+"",stats.getTotalGrass()+"",stats.getAverageChildren()+"",
                    stats.returnGenotypesList(1)+"",stats.getAverageLifespan()+"",stats.getAverageEnergy()+""};
            writer.writeNext(data);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
