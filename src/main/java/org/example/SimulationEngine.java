package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {

    protected ExecutorService executorService = Executors.newFixedThreadPool(4);
    protected List<Simulation> simulations = new ArrayList<>();
    protected List<Thread> threads = new ArrayList<>();

    public SimulationEngine(List<Simulation> simulations){
        this.simulations=simulations;

    }


    public void runAsync() throws InterruptedException {
        for( Simulation currSim : simulations){
            Thread engineThread = new Thread(currSim);
            threads.add(engineThread);
            engineThread.start();

        }
//       awaitSimulationsEnd();

    }


}
