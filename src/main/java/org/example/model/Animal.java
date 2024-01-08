package org.example.model;

import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;

public class Animal implements WorldElement {

    private final String geneticCode;
    private final int geneticLength;

    private int currentGenome;
    private int energy;

    public List<Animal> children = new LinkedList<>();

    public List<Animal> parents = new LinkedList<>();

    private MapDirection orientation;
    private Vector2d position;

    public Animal(Vector2d Position, String geneticCode, int energy) {
        this.orientation = MapDirection.NORTH;
        this.position = Position;
        this.geneticCode = geneticCode;
        this.geneticLength = geneticCode.length();
        this.energy = energy;
    }

    @Override
    public String toString() {
        return orientation.toString();
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(Vector2d newPosition) {
        position = newPosition;
        orientation = nextOrientation();
        currentGenome = (currentGenome + 1) % geneticLength;
    }

    public MapDirection nextOrientation(){
        int turns = geneticCode.charAt(currentGenome) - '0';
        MapDirection newOrientation = orientation;
        for (int i = 0; i < turns; i++) {
            newOrientation = newOrientation.next();
        }
        return newOrientation;
    }

    public Vector2d nextPosition(){
        return position.add(nextOrientation().toUnitVector());
    }


    @Override
    public Vector2d getPosition() {
        return position;
    }

    public String getGeneticCode() {
        return geneticCode;
    }

    public int getGeneticLength() {
        return geneticLength;
    }

    public int getCurrentGenomeIndex() {
        return currentGenome;
    }

    public char getCurrentGenome() {
        return geneticCode.charAt(currentGenome);
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void subtractEnergy(int energy) {
        this.energy -= energy;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
    }

    public void setCurrentGenome(int currentGenome) {
        this.currentGenome = currentGenome;
    }

    public int getChildrenAmount(){
        return children.size();
    }

    public Set<Animal> getOffspring(){
        Set<Animal> allOfsprings = new HashSet<>(children);
        for (Animal currChildren : children){
            allOfsprings.addAll(currChildren.getOffspring());
        }
        return allOfsprings;
    }

    public int getOffspringsAmount(){
        int Offspring=0;
        Set<Animal> allOffsprings=getOffspring();
        Offspring=allOffsprings.size();
        return Offspring;
    }


    public void setOppositeOrientation() {
        this.orientation = this.orientation.opposite();
    }
}
