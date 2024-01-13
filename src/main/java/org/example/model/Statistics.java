package org.example.model;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.round;

public class Statistics {
    WorldMap worldMap;
    private int totalAnimals;
    private int totalGrass;
    private int freeFields;
    private Map<String, Integer> genotypeCounts;
    private double averageEnergy;
    private double averageLifespan;
    private double averageChildren;
    private List<Animal> deadAnimals;

    private List<Animal> prevAnimals;

    public Statistics(WorldMap worldMap){
        this.worldMap = worldMap;
        this.genotypeCounts = new HashMap<>();
        this.deadAnimals = new ArrayList<>();
        this.prevAnimals = new ArrayList<>();
    }

    public void update(){
        totalAnimals = worldMap.getAnimals().size();
        totalGrass = worldMap.getGrasses().size();
        freeFields = calculateFreeFields();
        updateDead();
        updateGenotypeCounts();
        updateAverages();
        prevAnimals = List.copyOf(worldMap.getAnimals());
    }

    private int calculateFreeFields(){
        final int[] freeFields = {0};
        worldMap.getFields().forEach((key, value) -> {
            if (value.animals.isEmpty()){
                freeFields[0] += 1;
            }
        });
        return freeFields[0];
    }

    private void updateGenotypeCounts(){
        genotypeCounts.clear();
        for (Animal animal : worldMap.getAnimals()){
            String currGenotype = animal.getGeneticCode();
            genotypeCounts.put(currGenotype, genotypeCounts.getOrDefault(currGenotype, 0)+1);
        }
    }

    public List<Map.Entry<String, Integer>> getTopGenotypes(int n){
        return genotypeCounts.entrySet().stream().sorted((a, b) -> b.getValue().compareTo(a.getValue())).limit(n).collect(Collectors.toList());
    }

    private void updateAverages(){
        averageEnergy = (double) round(calculateAverageEnergy() * 100) /100;
        averageLifespan = (double) round(calculateAverageLifespan() * 100) /100;
        //averageChildren = (double) round(calculateAverageChildren() * 100) /100;
    }

    private double calculateAverageEnergy(){
        if (worldMap.getAnimals().isEmpty()) return 0;
        double totalEnergy = 0;

        for (Animal animal : worldMap.getAnimals()){
            totalEnergy += animal.getEnergy();
        }

        return totalEnergy / worldMap.getAnimals().size();
    }

    private double calculateAverageLifespan(){
        if (deadAnimals.isEmpty()) return 0;
        double totalLifespan = 0;

        for (Animal animal : deadAnimals){
            totalLifespan += animal.getAge();
        }

        return totalLifespan / deadAnimals.size();
    }

    private double calculateAverageChildren() {
        if (worldMap.getAnimals().isEmpty()) {
            return 0;
        }

        double totalDescendants = 0;

        for (Animal animal : worldMap.getAnimals()) {
            totalDescendants += calculateDescendantsCount(animal);
        }

        return totalDescendants / worldMap.getAnimals().size();
    }

    private int calculateDescendantsCount(Animal animal) {
        int descendantsCount = animal.getChildrenAmount();

        for (Animal child : animal.getOffspring()) {
            descendantsCount += calculateDescendantsCount(child);
        }

        return descendantsCount;
    }

    private void updateDead(){
        List<Animal> newDeadAnimals = prevAnimals.stream().filter(animal -> !worldMap.getAnimals().contains(animal)).toList();
        deadAnimals.addAll(newDeadAnimals);
    }

    public int getTotalAnimals() {
        return totalAnimals;
    }

    public int getTotalGrass() {
        return totalGrass;
    }

    public int getFreeFields() {
        return freeFields;
    }

    public Map<String, Integer> getGenotypeCounts() {
        return genotypeCounts;
    }

    public double getAverageEnergy() {
        return averageEnergy;
    }

    public double getAverageLifespan() {
        return averageLifespan;
    }

    public double getAverageChildren() {
        return averageChildren;
    }

    public List<Animal> getDeadAnimals() {
        return deadAnimals;
    }
}
