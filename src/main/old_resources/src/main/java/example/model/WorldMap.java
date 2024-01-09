package example.model;

public interface WorldMap {

    void dayCycle();

    void move(Animal animal);

    Vector2d canMoveTo(Animal animal);

}
