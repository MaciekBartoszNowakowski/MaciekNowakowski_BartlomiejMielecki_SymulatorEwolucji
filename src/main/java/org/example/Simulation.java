package org.example;

import org.example.model.WorldMap;

public class Simulation implements Runnable {

    private WorldMap worldMap;

    public Simulation(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void run() {
        while (true) {
            worldMap.dayCycle();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
