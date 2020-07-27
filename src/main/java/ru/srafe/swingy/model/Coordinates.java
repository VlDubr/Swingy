package ru.srafe.swingy.model;

import ru.srafe.swingy.controller.GameMap;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

public class Coordinates implements Serializable {

    private static final long serialVersionUID = 2L;

    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(int mapSize) {
        Random rand = new Random();
        x = rand.nextInt(mapSize);
        y = rand.nextInt(mapSize);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static boolean checkCoordinate(Coordinates c, int mapSize) {
        return c.getX() >= 0 && c.getY() >= 0 && c.getX() < mapSize && c.getY() < mapSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
