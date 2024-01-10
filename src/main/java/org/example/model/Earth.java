package org.example.model;

public class Earth extends AbstractWorldMap{
    public Earth(int height, int width, int startEnergy, int animalQuantity, int geneticLength, int grassPerDay, int energyFromGrass, int energyToReproduce, double energyUsed, MutationSystem mutationSystem) {
        super(height, width, startEnergy, animalQuantity, geneticLength, grassPerDay, energyFromGrass, energyToReproduce, energyUsed, mutationSystem);
    }


    public Vector2d canMoveTo(Animal animal){
        Vector2d newPosition = animal.nextPosition();
        Vector2d oldPosition = animal.getPosition();
        Vector2d correctPosition = null;
        if (super.canMoveTo(animal).equals(newPosition)){
            return newPosition;
        }
        if (!(newPosition.getY() >= 0 && newPosition.getY() < height)) {
            animal.setOppositeOrientation();
        }
        if (!(newPosition.getX() >= 0 && newPosition.getX() < width)){
            if (newPosition.getX() < 0){
                if (newPosition.getY() >= 0 && newPosition.getY() < height){
                    correctPosition = new Vector2d(width-1, newPosition.getY());
                } else {
                    correctPosition = new Vector2d(width-1, oldPosition.getY());
                }

            } else {
                if (newPosition.getY() >= 0 && newPosition.getY() < height){
                    correctPosition = new Vector2d(0, newPosition.getY());
                } else {
                    correctPosition = new Vector2d(0, oldPosition.getY());
                }
            }
        } else {
            return oldPosition;
        }
        return correctPosition;
    }

    @Override
    public String toString() {
        return "Earth{" +
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
