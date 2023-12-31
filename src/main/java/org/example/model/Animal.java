package org.example.model;

import java.util.List;
import java.util.LinkedList;

public class Animal implements WorldElement {

    private final String geneticCode;
    private final int geneticLength;

    private int currentGenome;
    private int energy;

    public List<Animal> children = new LinkedList<>();

    public List<Animal> parents = new LinkedList<>();

    private MapDirection orientation = MapDirection.NORTH;
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

    public void move() {
        int turns = geneticCode.charAt(currentGenome) - '0';
        for (int i = 0; i < turns; i++) {
            orientation.next();
        }
        position.add(orientation.toUnitVector());
        currentGenome = (currentGenome + 1) % geneticLength;
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


}
