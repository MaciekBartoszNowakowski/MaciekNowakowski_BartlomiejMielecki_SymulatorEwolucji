package example.model;

import java.util.ArrayList;
import java.util.List;

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

    // stawia zwierze na polu
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


    private List<Animal> sortByStronger(List<Animal> animals){
        List<Animal> animalsSorted = new ArrayList<>(animals);
        animalsSorted.sort((a, b) -> {
            if (a.getEnergy() > b.getEnergy()){
                return 1;
            } else if (a.getEnergy() < b.getEnergy()) {
                return  -1;
            } else {
                if (a.getChildrenAmount() > b.getChildrenAmount()){
                    return 1;
                } else if (a.getChildrenAmount() < b.getChildrenAmount()) {
                    return -1;
                }
            }
            return 0;
        }
        );
        return animalsSorted;
    }

    public void eatGrass(){
        if (animals.isEmpty()) return;
        List<Animal> sortedAnimals = sortByStronger(animals);
        sortedAnimals.get(sortedAnimals.size()-1).addEnergy(energyFromGrass);
        this.removeGrass();
    }

    public List<Animal> reproduction(){
        if (animals.isEmpty()) return null;
        ReproductionSystem rp = new ReproductionSystem(energyUsed, mutationSystem);
        List<Animal> children = new ArrayList<>();
        List<Animal> sortedAnimals = sortByStronger(animals);
        int i = sortedAnimals.size() - 1;
        while (i > 1){
            Animal parent1 = sortedAnimals.remove(i);
            if (parent1.getEnergy() < energyToReproduce) return children;
            Animal parent2 = sortedAnimals.remove(i-1);
            Animal child = rp.reproduce(parent1, parent2);
            animals.add(child);
            children.add(child);
            i -= 2;
        }
        return children;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }
}