package org.example.model;

import java.util.Map;
import java.util.UUID;

public interface WorldMap {

    void dayCycle();

    void move(Animal animal);

    Vector2d canMoveTo(Animal animal);

}
