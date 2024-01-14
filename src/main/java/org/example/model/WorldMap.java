package org.example.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface WorldMap {

    void dayCycle();

    void move(Animal animal);

    Vector2d canMoveTo(Animal animal);


    void register(MapChangeListener listener);
    void unregister(MapChangeListener listener);


    void mapChanged(String description);


    int getHeight();

    int getWidth();

    Statistics getStatistics();
    int getWorldAge();

    Map<Vector2d, MapField> getFields();

    List<Animal> getAnimals();

    List<Grass> getGrasses();
}
