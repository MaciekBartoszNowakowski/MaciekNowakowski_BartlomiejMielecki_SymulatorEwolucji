package example.model;

public class Grass implements WorldElement {


    private final Vector2d position;

    public Grass(Vector2d Position) {
        this.position = Position;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString(){
        return "*";
    }


}
