package org.example.model;

import java.util.*;

public class MapField {
    public List<Animal> animals = new ArrayList<>(); // lista zwierząt na polu
    public boolean preferred = false; // pole jest preferowane czy nie (szansa na trawe)
    public Grass grass = null;
    private final int grassPerDay;
    private final int energyFromGrass;
    private final int energyToReproduce;
    private final double energyUsed;
    private final MutationSystem mutationSystem;

    public MapField(int grassPerDay, int energyFromGrass, int energyToReproduce, double energyUsed, MutationSystem mutationSystem){
        this.grassPerDay = grassPerDay;
        this.energyFromGrass = energyFromGrass;
        this.energyToReproduce = energyToReproduce;
        this.energyUsed = energyUsed;
        this.mutationSystem = mutationSystem;
    }

    public void placeAnimal(Animal animal){
        animals.add(animal);
    }

    public void removeAnimal(Animal animal){
        animals.remove(animal);
    }

    public void placeGrass(Grass grass){
        this.grass = grass;
    }

    public void removeGrass(){
        this.grass = null;
    }

    public Grass eatGrass(){
        if (animals.isEmpty()) return null;
        if (grass == null) return null;
        Collections.sort(animals);
        Animal animal = animals.get(animals.size()-1);
        animal.addEnergy(energyFromGrass);
        animal.eatGrass();
        Grass grass1 = grass;
        this.removeGrass();
        return grass1;
    }

    public List<Animal> reproduction(){
        if (animals.size()<2) return null;
        ReproductionSystem rp = new ReproductionSystem(energyUsed, mutationSystem);
        Collections.sort(animals);
        List<Animal> children = new ArrayList<>();
        Animal parent1 = animals.get(animals.size()-1);
        if (parent1.getEnergy() < energyToReproduce) return null;
        Animal parent2 = animals.get(animals.size()-2);
        Animal child = rp.reproduce(parent1, parent2);
        children.add(child);
        return children;
    }
    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }


    public boolean isGrass(){
        return grass != null;
    }

    @Override
    public String toString() {
        List<String> s = new ArrayList<>();
        animals.forEach(animal -> {
            s.add(animal.toString());
        });
        return s.toString();
    }
}
