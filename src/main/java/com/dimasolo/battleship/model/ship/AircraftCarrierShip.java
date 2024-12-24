package com.dimasolo.battleship.model.ship;

import com.dimasolo.battleship.model.BoundCoordinate;
import com.dimasolo.battleship.model.ShipCoordinate;

import java.util.ArrayList;
import java.util.List;

public class AircraftCarrierShip implements Ship {

    private final List<ShipCoordinate> shipCoordinates;
    private final List<BoundCoordinate> boundCoordinates;

    @Override
    public String toString() {
        return "AircraftCarrierShip{" +
                "shipCoordinates=" + shipCoordinates +
                ", boundCoordinates=" + boundCoordinates +
                '}';
    }

    public AircraftCarrierShip(List<ShipCoordinate> shipCoordinates, List<BoundCoordinate> boundCoordinates) {
        this.shipCoordinates = shipCoordinates;
        this.boundCoordinates = boundCoordinates;
    }

    public AircraftCarrierShip(List<ShipCoordinate> shipCoordinates) {
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
