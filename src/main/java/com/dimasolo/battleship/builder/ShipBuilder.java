package com.dimasolo.battleship.builder;

import com.dimasolo.battleship.model.BoundCoordinate;
import com.dimasolo.battleship.model.ShipCoordinate;
import com.dimasolo.battleship.model.ship.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShipBuilder {

    private static final Random RANDOM = new Random();

    public Ship buildShip(int shipLength) throws Exception {
        return switch (shipLength) {
            case 4 -> {
                List<ShipCoordinate> shipCoordinates = generateShipCoordinates(shipLength);
                List<BoundCoordinate> boundCoordinates = generateShipBoundsCoordinates(shipCoordinates);
                yield new AircraftCarrierShip(shipCoordinates, boundCoordinates);
            }
            case 3 -> {
                List<ShipCoordinate> shipCoordinates = generateShipCoordinates(shipLength);
                List<BoundCoordinate> boundCoordinates = generateShipBoundsCoordinates(shipCoordinates);
                yield new BattleShip(shipCoordinates, boundCoordinates);
            }
            case 2 -> {
                List<ShipCoordinate> shipCoordinates = generateShipCoordinates(shipLength);
                List<BoundCoordinate> boundCoordinates = generateShipBoundsCoordinates(shipCoordinates);
                yield new CruiserShip(shipCoordinates, boundCoordinates);
            }
            case 1 -> {
                List<ShipCoordinate> shipCoordinates = generateShipCoordinates(shipLength);
                List<BoundCoordinate> boundCoordinates = generateShipBoundsCoordinates(shipCoordinates);
                yield new DestroyerShip(shipCoordinates, boundCoordinates);
            }
            default -> throw new Exception("Unknown Ship Type. Ship length is %s".formatted(shipLength));
        };
    }

    private List<ShipCoordinate> generateShipCoordinates(int shipLength) {
        List<ShipCoordinate> shipCoordinates = new ArrayList<>();
        boolean isHorizontal = Math.random() > 0.5;
        int startCoordinate = RANDOM.nextInt(10);
        while ((startCoordinate + shipLength - 1) > 9) {
            startCoordinate = RANDOM.nextInt(10);
        }
        if (isHorizontal) {
            int startCoordinateY = RANDOM.nextInt(10);
            for (int coordinate = startCoordinate; coordinate <= (startCoordinate + shipLength - 1); coordinate++) {
                ShipCoordinate shipCoordinate = new ShipCoordinate(coordinate, startCoordinateY);
                shipCoordinates.add(shipCoordinate);
            }
        } else {
            int startCoordinateX = RANDOM.nextInt(10);
            for (int coordinate = startCoordinate; coordinate <= (startCoordinate + shipLength - 1); coordinate++) {
                ShipCoordinate shipCoordinate = new ShipCoordinate(startCoordinateX, coordinate);
                shipCoordinates.add(shipCoordinate);
            }
        }
        return shipCoordinates;
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    private boolean isPartOfShip(int x, int y, List<ShipCoordinate> shipCoordinates) {
        return shipCoordinates.stream().anyMatch(shipCoordinate -> shipCoordinate.getX() == x && shipCoordinate.getY() == y);
    }

    private List<BoundCoordinate> generateShipBoundsCoordinates(List<ShipCoordinate> shipCoordinates) {
        List<BoundCoordinate> boundCoordinates = new ArrayList<>();
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},          {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };
        for (ShipCoordinate shipCoordinate : shipCoordinates) {
            for (int[] direction : directions) {
                int newX = shipCoordinate.getX() + direction[0];
                int newY = shipCoordinate.getY() + direction[1];
                if (isValidCoordinate(newX, newY) && !isPartOfShip(newX, newY, shipCoordinates)) {
                    BoundCoordinate boundCoordinate = new BoundCoordinate(newX, newY);
                    if (!boundCoordinates.contains(boundCoordinate)) {
                        boundCoordinates.add(boundCoordinate);
                    }
                }
            }
        }
        return boundCoordinates;
    }
}
