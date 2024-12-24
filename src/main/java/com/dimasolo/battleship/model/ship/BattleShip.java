package com.dimasolo.battleship.model.ship;

import com.dimasolo.battleship.model.BoundCoordinate;
import com.dimasolo.battleship.model.ShipCoordinate;

import java.util.ArrayList;
import java.util.List;

public class BattleShip implements Ship{

    private final List<ShipCoordinate> shipCoordinates;

    @Override
    public String toString() {
        return "BattleShip{" +
                "shipCoordinates=" + shipCoordinates +
                ", boundCoordinates=" + boundCoordinates +
                '}';
    }

    private final List<BoundCoordinate> boundCoordinates;

    public BattleShip(List<ShipCoordinate> shipCoordinates, List<BoundCoordinate> boundCoordinates) {
        this.shipCoordinates = shipCoordinates;
        this.boundCoordinates = boundCoordinates;
    }

    public BattleShip(List<ShipCoordinate> shipCoordinates) {
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
    public List<BoundCoordinate> getBoundsCoordinates() {
        return this.boundCoordinates;
    }
}
