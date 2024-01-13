package org.example.model;

import java.util.Random;

public class HellGate extends AbstractWorldMap{
    public HellGate(int height, int width, int startEnergy, int animalQuantity, int geneticLength, int grassPerDay, int energyFromGrass, int energyToReproduce, double energyUsed, MutationSystem mutationSystem) {
        super(height, width, startEnergy, animalQuantity, geneticLength, grassPerDay, energyFromGrass, energyToReproduce, energyUsed, mutationSystem);
    }


    @Override
    public Vector2d canMoveTo(Animal animal){
        animal.setEnergy((int) (animal.getEnergy()*energyUsed));
        Vector2d newPosition = animal.nextPosition();
        Vector2d oldPosition = animal.getPosition();
        Vector2d correctPosition = oldPosition;
        if (super.canMoveTo(animal).equals(newPosition)){
            return newPosition;
        }

        if (fields.size()==1) return oldPosition;

        while (correctPosition.equals(oldPosition)) {
            Random rand = new Random();
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            correctPosition = new Vector2d(x, y);
        }
        return correctPosition;
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
