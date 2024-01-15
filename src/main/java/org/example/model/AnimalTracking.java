package org.example.model;

import java.util.Arrays;
import java.util.List;

public class AnimalTracking {
    private Animal animal;
    private WorldMap worldMap;

    private boolean isDead = false;
    private int deathDay = 0;
    public AnimalTracking(Animal animal, WorldMap worldMap){
        this.animal = animal;
        this.worldMap = worldMap;
    }

    public List<String> returnAnimalStatistics(){
        return Arrays.asList(animal.getGeneticCode(), String.valueOf(animal.getCurrentGenome()), String.valueOf(animal.getGrassCount()), String.valueOf(animal.getChildrenAmount()), String.valueOf(animal.getOffspringsAmount()), String.valueOf(animal.getAge()), animalDeath());
    }

    public String animalDeath(){
        if (isDead) return String.valueOf(deathDay);
        if (!worldMap.getAnimals().contains(animal)) {
            deathDay = worldMap.getWorldAge();
            isDead = true;
            return String.valueOf(deathDay);
        }
        return "";
    }

    public boolean getIsDead(){
        return isDead;
    }
    public Animal getAnimal(){
        return animal;
    }
}
