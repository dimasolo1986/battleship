package com.dimasolo.battleship.model;

public record BoundCoordinate(int x, int y) implements Coordinate {
    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }
}
