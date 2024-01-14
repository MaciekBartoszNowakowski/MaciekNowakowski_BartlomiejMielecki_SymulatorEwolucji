package org.example;

import org.example.model.WorldMap;

public class Simulation implements Runnable {

    private WorldMap worldMap;

    public boolean isPause=false;

    public Simulation(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(200);
                while (!isPause) {
                    worldMap.dayCycle();
                    try {
                        Thread.sleep(200);
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
