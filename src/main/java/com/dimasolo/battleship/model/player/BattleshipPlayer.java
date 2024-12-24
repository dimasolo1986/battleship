package com.dimasolo.battleship.model.player;

import com.dimasolo.battleship.model.Coordinate;
import com.dimasolo.battleship.model.FieldCoordinate;
import com.dimasolo.battleship.model.Hit;
import com.dimasolo.battleship.model.ship.Ship;

import java.util.List;
import java.util.Stack;
import java.util.UUID;

import static com.dimasolo.battleship.model.BattleShipFieldConstants.*;

public class BattleshipPlayer implements Player {

    private final String playerId;

    private Player rival;

    @Override
    public void setRivalId(String rivalId) {
        this.rivalId = rivalId;
    }

    @Override
    public String getRivalId() {
        return rivalId;
    }

    @Override
    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    @Override
    public PlayerState getPlayerState() {
        return this.playerState;
    }

    private String rivalId;

    @Override
    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    private List<Ship> ships;
    private final PlayerType playerType;

    private PlayerState playerState;

    private final Stack<Coordinate> furtherCoordinatesToHit;

    private Coordinate firstHitCoordinate;

    @Override
    public Hit getHit() {
        return hit;
    }

    @Override
    public void setFirstHitCoordinate(Coordinate coordinate) {
        this.firstHitCoordinate = coordinate;
    }

    @Override
    public Coordinate getFirstHitCoordinate() {
        return this.firstHitCoordinate;
    }

    @Override
    public void clearFurtherCoordinatesToHit() {
        this.furtherCoordinatesToHit.clear();
    }

    @Override
    public void setHit(Hit hit) {
        this.hit = hit;
    }

    private Hit hit;

    private int [][] gameField;

    public BattleshipPlayer(List<Ship> ships, PlayerType playerType, PlayerState playerState) {
        this.ships = ships;
        this.playerType = playerType;
        this.playerState = playerState;
        this.playerId = UUID.randomUUID().toString();
        this.furtherCoordinatesToHit = new Stack<>();
        this.hit = Hit.ZERO;
        initGameField();
    }

    @Override
    public void buildFurtherCoordinatesToHit(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        if (this.hit == Hit.FIRST) {
            if (x + 1 <= 9 && this.gameField[x+1][y] != GOOD_TURN && this.gameField[x+1][y] != PAST_TURN) {
                furtherCoordinatesToHit.add(new FieldCoordinate(x + 1, y));
            }
            if (x - 1 >= 0 && this.gameField[x-1][y] != GOOD_TURN && this.gameField[x-1][y] != PAST_TURN) {
                furtherCoordinatesToHit.add(new FieldCoordinate(x - 1, y));
            }
            if (y + 1 <= 9 && this.gameField[x][y+1] != GOOD_TURN && this.gameField[x][y+1] != PAST_TURN) {
                furtherCoordinatesToHit.add(new FieldCoordinate(x, y + 1));
            }
            if (y - 1 >= 0 && this.gameField[x][y-1] != GOOD_TURN && this.gameField[x][y-1] != PAST_TURN) {
                furtherCoordinatesToHit.add(new FieldCoordinate(x, y - 1));
            }
        }
        if (this.hit == Hit.SECOND) {
            boolean isHorizontal = this.firstHitCoordinate.getX() == coordinate.getX();
            if(isHorizontal) {
                int maxY = Math.max(this.firstHitCoordinate.getY(), coordinate.getY());
                int minY = Math.min(this.firstHitCoordinate.getY(), coordinate.getY());
                if(maxY + 1 <= 9 && this.gameField[x][maxY+1] != GOOD_TURN && this.gameField[x][maxY+1] != PAST_TURN) {
                    furtherCoordinatesToHit.add(new FieldCoordinate(x, maxY+1));
                }
                if(minY - 1 >= 0 && this.gameField[x][minY - 1] != GOOD_TURN && this.gameField[x][minY - 1] != PAST_TURN) {
                    furtherCoordinatesToHit.add(new FieldCoordinate(x, minY - 1));
                }
            } else {
                int maxX = Math.max(this.firstHitCoordinate.getX(), coordinate.getX());
                int minX = Math.min(this.firstHitCoordinate.getX(), coordinate.getX());
                if(maxX + 1 <= 9 && this.gameField[maxX + 1][y] != GOOD_TURN && this.gameField[maxX + 1][y] != PAST_TURN) {
                    furtherCoordinatesToHit.add(new FieldCoordinate(maxX + 1, y));
                }
                if(minX - 1 >= 0 && this.gameField[minX - 1][y] != GOOD_TURN && this.gameField[minX - 1][y] != PAST_TURN) {
                    furtherCoordinatesToHit.add(new FieldCoordinate(minX - 1, y));
                }
            }
        }
    }

    @Override
    public Stack<Coordinate> getFurtherCoordinatesToHit() {
        return this.furtherCoordinatesToHit;
    }

    @Override
    public PlayerType getPlayerType() {
        return this.playerType;
    }

    @Override
    public void setRival(Player player) {
        this.rival = player;
    }

    @Override
    public Player getRival() {
        return this.rival;
    }

    @Override
    public List<Ship> getShips() {
        return this.ships;
    }

    private void initGameField() {
        this.gameField = new int [10][10];
        this.ships.forEach(ship -> ship.getShipCoordinates().forEach(shipCoordinate -> gameField[shipCoordinate.getX()][shipCoordinate.getY()] = SHIP_COORDINATE));
    }
    @Override
    public int[][] getGameField() {
        return gameField;
    }

    @Override
    public void setGameField(int x, int y, int value) {
        this.gameField[x][y] = value;
    }

    @Override
    public String getPlayerId() {
        return this.playerId;
    }
}
