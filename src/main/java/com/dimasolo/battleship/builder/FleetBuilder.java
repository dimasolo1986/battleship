package com.dimasolo.battleship.builder;

import com.dimasolo.battleship.model.ship.Ship;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class FleetBuilder {

    private final ShipBuilder shipBuilder;

    @Getter
    private final int [][] gameField;


    public FleetBuilder(ShipBuilder shipBuilder) {
        this.shipBuilder = shipBuilder;
        this.gameField = new int [10][10];
    }

    public List<Ship> buildShipFleet() throws Exception {
        List<Ship> ships = new ArrayList<>();
        // Build 1 AircraftCarrier ship
        ships.addAll(createShips(1, 4));
        // Build 2 BattleShip ships
        ships.addAll(createShips(2, 3));
        // Build 3 CruiserShip ships
        ships.addAll(createShips(3, 2));
        // Build 4 DestroyerShip ships
        ships.addAll(createShips(4, 1));
        return ships;
    }

    private List<Ship> createShips(int shipNumber, int shipLength) throws Exception {
        List<Ship> ships = new ArrayList<>();
        for (int i = 0; i < shipNumber; i++) {
            Ship ship = shipBuilder.buildShip(shipLength);
            while (isShipNotValid(ship)) {
                ship = shipBuilder.buildShip(shipLength);
            }
            fillGameField(ship);
            ships.add(ship);
        }
        return ships;
    }

    private boolean isShipNotValid(Ship ship) {
       return ship.getShipCoordinates().stream().anyMatch(shipCoordinate -> gameField[shipCoordinate.getX()][shipCoordinate.getY()] == 1);
    }

    private void fillGameField(Ship ship) {
        ship.getShipCoordinates().forEach(shipCoordinate -> gameField[shipCoordinate.getX()][shipCoordinate.getY()] = 1);
        ship.getBoundsCoordinates().forEach(boundCoordinate -> gameField[boundCoordinate.getX()][boundCoordinate.getY()] = 1);
    }
}
