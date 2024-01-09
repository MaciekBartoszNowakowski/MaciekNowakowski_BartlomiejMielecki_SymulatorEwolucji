package example.model;

import java.util.Objects;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }


    //Getter x
    public int getX() {
        return this.x;
    }


    //Getter y
    public int getY() {
        return this.y;
    }


    public String toString() {
        return "(" + x + "," + y + ")";

    }


    public boolean precedes(Vector2d other) {
        int secondY = other.getY();
        int secondX = other.getX();
        if (this.x <= secondX && this.y <= secondY) {
            return true;
        } else {
            return false;
        }

    }

    public boolean follows(Vector2d other) {
        int secondY = other.getY();
        int secondX = other.getX();
        if (this.x >= secondX && this.y >= secondY) {
            return true;
        } else {
            return false;
        }
    }

    public Vector2d add(Vector2d other) {
        int newX = this.x + other.getX();
        int newY = this.y + other.getY();
        return new Vector2d(newX, newY);
    }

    public Vector2d substract(Vector2d other) {
        int newX = this.x - other.getX();
        int newY = this.y - other.getY();
        return new Vector2d(newX, newY);
    }

    public Vector2d upperRight(Vector2d other) {
        int newX = Math.max(this.x, other.getX());
        int newY = Math.max(this.y, other.getY());
        return new Vector2d(newX, newY);
    }

    public Vector2d lowerLeft(Vector2d other) {
        int newX = Math.min(this.x, other.getX());
        int newY = Math.min(this.y, other.getY());
        return new Vector2d(newX, newY);
    }

    public Vector2d opposite() {
        return new Vector2d(this.x * -1, this.y * -1);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        if (this.x == that.x & this.y == that.y) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
