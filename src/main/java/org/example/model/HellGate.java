package org.example.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HellGate extends AbstractWorldMap{
    public HellGate(int height, int width, int startEnergy, int animalQuantity, int geneticLength, int grassPerDay, int energyFromGrass, int energyToReproduce, double energyUsed, MutationSystem mutationSystem, int startingGrassAmount) {
        super(height, width, startEnergy, animalQuantity, geneticLength, grassPerDay, energyFromGrass, energyToReproduce, energyUsed, mutationSystem, startingGrassAmount);
    }


    @Override
    public Vector2d canMoveTo(Animal animal){
        Vector2d newPosition = animal.nextPosition();
        Vector2d oldPosition = animal.getPosition();
        Vector2d correctPosition = oldPosition;
        if (super.canMoveTo(animal).equals(newPosition)){
            return newPosition;
        }
        animal.setEnergy((int) (animal.getEnergy() - animal.getEnergy()*energyUsed));
        if (fields.size()==1) return oldPosition;

        correctPosition = generateRandomFreePosition();

        while (correctPosition != null && correctPosition.equals(oldPosition)) {
            Random rand = new Random();
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            correctPosition = new Vector2d(x, y);
        }
        return correctPosition;
    }

    private Vector2d generateRandomFreePosition(){
        List<Vector2d> positionsList = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Vector2d position = new Vector2d(j, i);
                if (fields.get(position).animals.isEmpty()){
                    positionsList.add(position);
                }
            }
        }
        if(positionsList.isEmpty()) return null;

        Random rand = new Random();
        int index = rand.nextInt(positionsList.size());
        return positionsList.get(index);
    }

    @Override
    public String toString() {
        return "HellGate{" +
                ", animals=" + animals +
                ", grasses=" + grasses +
                ", width=" + width +
                ", height=" + height +
                ", geneticLength=" + geneticLength +
                ", startEnergy=" + startEnergy +
                ", animalQuantity=" + animalQuantity +
                ", grassPerDay=" + grassPerDay +
                ", energyFromGrass=" + energyFromGrass +
                ", energyToReproduce=" + energyToReproduce +
                ", energyUsed=" + energyUsed +
                ", mutationSystem=" + mutationSystem +
                '}';
    }
}
