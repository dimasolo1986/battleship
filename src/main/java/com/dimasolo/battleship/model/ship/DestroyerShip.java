package com.dimasolo.battleship.model.ship;

import com.dimasolo.battleship.model.BoundCoordinate;
import com.dimasolo.battleship.model.ShipCoordinate;

import java.util.ArrayList;
import java.util.List;

public class DestroyerShip implements Ship {

    private final List<ShipCoordinate> shipCoordinates;
    private final List<BoundCoordinate> boundCoordinates;

    public DestroyerShip(List<ShipCoordinate> shipCoordinates, List<BoundCoordinate> boundCoordinates) {
        this.shipCoordinates = shipCoordinates;
        this.boundCoordinates = boundCoordinates;
    }

    public DestroyerShip(List<ShipCoordinate> shipCoordinates) {
        this.shipCoordinates = shipCoordinates;
        this.boundCoordinates = new ArrayList<>();
    }
    @Override
    public int getLength() {
        return this.shipCoordinates.size();
    }

    @Override
    public List<ShipCoordinate> getShipCoordinates() {
        return this.shipCoordinates;
    }

    @Override
    public String toString() {
        return "DestroyerShip{" +
                "shipCoordinates=" + shipCoordinates +
                ", boundCoordinates=" + boundCoordinates +
                '}';
    }

    @Override
    public List<BoundCoordinate> getBoundsCoordinates() {
        return this.boundCoordinates;
    }
}
