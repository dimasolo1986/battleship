package com.dimasolo.battleship.model.player;

import com.dimasolo.battleship.model.Coordinate;
import com.dimasolo.battleship.model.Hit;
import com.dimasolo.battleship.model.ship.Ship;

import java.util.List;
import java.util.Stack;

public interface Player {

    List<Ship> getShips();

    int [][] getGameField();

    void setGameField(int x, int y, int value);

    String getPlayerId();

    void setRivalId(String rivalId);

    String getRivalId();

    void setPlayerState(PlayerState playerState);

    PlayerState getPlayerState();

    void setShips(List<Ship> ships);

    void setHit(Hit hit);

    Hit getHit();

    void setFirstHitCoordinate(Coordinate coordinate);
    Coordinate getFirstHitCoordinate();


    void clearFurtherCoordinatesToHit();

    void buildFurtherCoordinatesToHit(Coordinate coordinate);

    Stack<Coordinate> getFurtherCoordinatesToHit();

    PlayerType getPlayerType();

    void setRival(Player player);

    Player getRival();

}
