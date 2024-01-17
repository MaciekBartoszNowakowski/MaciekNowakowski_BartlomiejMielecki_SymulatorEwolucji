package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

public class MapFieldTest {
    @Test
    public void ReproductionTest(){
        MapField mapField = new MapField(1, 1, 10, 0.5, new SlightMutation(2, 7));
        Animal parent1 = new Animal(null, "64345352", 20);
        mapField.placeAnimal(parent1);
        Animal parent2 = new Animal(null, "41363474", 20);
        mapField.placeAnimal(parent2);
        List<Animal> children =  mapField.reproduction();
        assertEquals(1, children.size());
        assertEquals(10, parent1.getEnergy());
        assertEquals(10, parent2.getEnergy());

        mapField = new MapField(1, 1, 10, 0.5, new SlightMutation(2, 7));
        parent1 = new Animal(null, "64347352", 20);
        mapField.placeAnimal(parent1);
        parent2 = new Animal(null, "41363472", 20);
        mapField.placeAnimal(parent2);
        Animal parent3 = new Animal(null, "41363474", 11);
        mapField.placeAnimal(parent3);
        children =  mapField.reproduction();
        assertEquals(1, children.size());
        assertEquals(10, parent1.getEnergy());
        assertEquals(10, parent2.getEnergy());
        assertEquals(11, parent3.getEnergy());
    }

    @Test
    public void eatGrassTest(){
        MapField mapField = new MapField(1, 10, 10, 0.5, new SlightMutation(2, 7));
        Animal animal1 = new Animal(null, "64347352", 20);
        Animal animal2 = new Animal(null, "64347352", 21);
        mapField.placeAnimal(animal1);
        mapField.placeAnimal(animal2);
        mapField.placeGrass(new Grass(null));
        mapField.eatGrass();
        assertEquals(20, animal1.getEnergy());
        assertEquals(31, animal2.getEnergy());
    }

}
