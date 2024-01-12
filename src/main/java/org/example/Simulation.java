package org.example;

import org.example.model.WorldMap;

public class Simulation implements Runnable {

    private WorldMap worldMap;

    boolean isPause=false;

    public Simulation(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
                while (!isPause) {
                    worldMap.dayCycle();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void setPause(boolean value){isPause=value;}
}
