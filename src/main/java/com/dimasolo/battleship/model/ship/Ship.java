package com.dimasolo.battleship.model.ship;

import com.dimasolo.battleship.model.BoundCoordinate;
import com.dimasolo.battleship.model.ShipCoordinate;

import java.util.List;

public interface Ship {

    int getLength();
    List<ShipCoordinate> getShipCoordinates();
    List<BoundCoordinate> getBoundsCoordinates();

}
