package example.model;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap{
    protected final Map<Vector2d, MapField> fields = new HashMap<>();
    protected List<Animal> animals = new ArrayList<>();
    protected List<Grass> grasses = new ArrayList<>();
    protected int width;
    protected int height;
    protected int geneticLength;
    protected int startEnergy;
    protected int animalQuantity;
    protected int grassPerDay;
    protected int energyFromGrass;
    protected int energyToReproduce;
    protected double energyUsed;
    protected MutationSystem mutationSystem;
    protected double equatorStart;
    protected double equatorEnd;

    public AbstractWorldMap(int height, int width, int startEnergy, int animalQuantity, int geneticLength,
                            int grassPerDay, int energyFromGrass, int energyToReproduce, double energyUsed, MutationSystem mutationSystem
                            ){
        this.height = height;
        this.width = width;
        this.startEnergy = startEnergy;
        this.animalQuantity = animalQuantity;
        this.geneticLength = geneticLength;
        this.grassPerDay = grassPerDay;
        this.energyFromGrass = energyFromGrass;
        this.energyToReproduce = energyToReproduce;
        this.energyUsed = energyUsed;
        this.mutationSystem = mutationSystem;
        double equatorSize = height * 0.2;
        this.equatorStart = height/2 - equatorSize/2;
        this.equatorEnd = height/2 + equatorSize/2;
        createMap();
        placeAnimals(animalQuantity);
    }

    private void createMap(){
        for (int i = 0; i < height; i++) {
            if (i >= equatorStart && i <= equatorEnd) {
                for (int j = 0; j < width; j++) {
                    MapField field = new MapField(grassPerDay, energyFromGrass, energyToReproduce, energyUsed, mutationSystem);
                    field.setPreferred(true);
                    fields.put(new Vector2d(j, i), field);
                }
            } else{
                for (int j = 0; j < width; j++) {
                    fields.put(new Vector2d(j, i), new MapField(grassPerDay, energyFromGrass, energyToReproduce, energyUsed, mutationSystem));
                }
            }
        }
        growGrass();
    }


    private void placeAnimals(int quantity){
        for (int i = 0; i < quantity; i++) {
            Random rand = new Random();
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            Vector2d position = new Vector2d(x, y);
            Animal animal = new Animal(position, generateNewGeneticCode(), startEnergy);
            MapField field = fields.get(position);
            field.placeAnimal(animal);
            animals.add(animal);
        }
    }

    public void move(Animal animal) {
        Vector2d position = animal.getPosition();
        MapField field = fields.get(position);
        field.removeAnimal(animal);
        Vector2d newPosition = canMoveTo(animal);
        animal.move(newPosition);
        position = animal.getPosition();
        field = fields.get(position);
        field.placeAnimal(animal);
    }
    
    private String generateNewGeneticCode(){
        String geneticCode = "";
        Random rand = new Random();
        for (int i = 0; i < geneticLength; i++) {
            geneticCode = geneticCode + rand.nextInt(8);
        }
        return geneticCode;
    }

    public Vector2d canMoveTo(Animal animal){
        Vector2d lowerLeft = new Vector2d(0 ,0);
        Vector2d upperRight = new Vector2d(width-1, height-1);
        Vector2d position = animal.nextPosition();
        if (position.follows(lowerLeft) && position.precedes(upperRight)){
            return position;
        }
        return animal.getPosition();
    }

    private void removeCorpses(){
        for (int i = 0; i < animals.size(); i++) {
            Animal animal = animals.get(i);
            animal.subtractEnergy(1);
            if (animal.getEnergy() <= 0){
                fields.get(animal.getPosition()).removeAnimal(animal);
                animals.remove(animal);
                i -= 1;
            }
        }
    }

    private void moveAnimals(){
        for(Animal animal : animals){
            move(animal);
        }
    }

    private void growGrass(){
        RandomPositionGeneratorForGrass randomPositionGeneratorForGrass = new RandomPositionGeneratorForGrass(fields, width, height, grassPerDay);

        for (Vector2d grassPosition : randomPositionGeneratorForGrass){
            Grass grass = new Grass(grassPosition);
            grasses.add(grass);
            fields.get(grassPosition).placeGrass(grass);
        }
    }

    public void dayCycle(){
        removeCorpses();
        moveAnimals();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                MapField currentField = fields.get(new Vector2d(j, i));
                currentField.eatGrass();
                List<Animal> newAnimals =  currentField.reproduction();
                if (newAnimals != null && !newAnimals.isEmpty()) animals.addAll(newAnimals);
            }
        }
        growGrass();
    }

    public Map<Vector2d, MapField> getFields() {
        return fields;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getGeneticLength() {
        return geneticLength;
    }

    public void setGeneticLength(int geneticLength) {
        this.geneticLength = geneticLength;
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    public void setStartEnergy(int startEnergy) {
        this.startEnergy = startEnergy;
    }

    public int getAnimalQuantity() {
        return animalQuantity;
    }

    public void setAnimalQuantity(int animalQuantity) {
        this.animalQuantity = animalQuantity;
    }

    public int getGrassPerDay() {
        return grassPerDay;
    }

    public void setGrassPerDay(int grassPerDay) {
        this.grassPerDay = grassPerDay;
    }

    public int getEnergyFromGrass() {
        return energyFromGrass;
    }

    public void setEnergyFromGrass(int energyFromGrass) {
        this.energyFromGrass = energyFromGrass;
    }

    public int getEnergyToReproduce() {
        return energyToReproduce;
    }

    public void setEnergyToReproduce(int energyToReproduce) {
        this.energyToReproduce = energyToReproduce;
    }

    public void setEnergyUsed(int energyUsed) {
        this.energyUsed = energyUsed;
    }

    public MutationSystem getMutationSystem() {
        return mutationSystem;
    }

    public void setMutationSystem(MutationSystem mutationSystem) {
        this.mutationSystem = mutationSystem;
    }

    public List<Grass> getGrasses() {
        return grasses;
    }
}
