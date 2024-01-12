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

    public void runSync(){
        for( Simulation currSim : simulations){
            currSim.run();
        }
    }

    public void runAsync() throws InterruptedException {
        for( Simulation currSim : simulations){
            Thread engineThread = new Thread(currSim);
            threads.add(engineThread);
            engineThread.start();

        }
//       awaitSimulationsEnd();

    }

    public void pause() throws InterruptedException {
        for(Thread currThread : threads){
            currThread.wait();
        }
    }

    public void go(){
        for(Thread currThread : threads){
            currThread.notify();
        }
    }


    public void runAsyncInThreadPool() throws InterruptedException {
        for(Simulation simulation : simulations){
            executorService.submit(simulation);
        }
//       awaitSimulationsEnd();
    }

    public void awaitSimulationsEnd() throws InterruptedException {
        for (Thread currTh : threads){
            currTh.join();
        }
        executorService.shutdown();
        try{
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        }catch(InterruptedException e){
            executorService.shutdownNow();
        }

    }

}
