package org.example;

import org.example.model.WorldMap;

public class Simulation {

    private WorldMap worldMap;

    public Simulation(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void run() {
        while (true) {
            worldMap.dayCycle();
        }
    }


}
