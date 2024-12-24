package com.dimasolo.battleship.service;

import com.dimasolo.battleship.builder.GameBuilder;
import com.dimasolo.battleship.model.Coordinate;
import com.dimasolo.battleship.model.FieldCoordinate;
import com.dimasolo.battleship.model.Hit;
import com.dimasolo.battleship.model.ShipCoordinate;
import com.dimasolo.battleship.model.game.Game;
import com.dimasolo.battleship.model.player.Player;
import com.dimasolo.battleship.model.ship.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static com.dimasolo.battleship.model.BattleShipFieldConstants.*;


@Service
public class BattleshipService {

    private final GameBuilder gameBuilder;

    private static final Random RANDOM = new Random();

    @Autowired
    public BattleshipService(GameBuilder gameBuilder) {
        this.gameBuilder = gameBuilder;
    }

    public Game createBattleshipGame() throws Exception {
        return this.gameBuilder.buildGame();
    }

    public boolean turn(Player player, Coordinate turn) {
        boolean isTurnSuccessful =  isTurnSuccessful(player, turn);
        if (isTurnSuccessful) {
            processSuccessfulPlayerTurn(player, turn);
        } else {
            processFailedPlayerTurn(player, turn);
        }
        return isTurnSuccessful;
    }

    public Coordinate generateCoordinate(Player player) {
        if(player.getFurtherCoordinatesToHit().isEmpty()) {
            int x = RANDOM.nextInt(10);
            int y = RANDOM.nextInt(10);
            while (player.getGameField()[x][y] == GOOD_TURN || player.getGameField()[x][y] == PAST_TURN) {
                x = RANDOM.nextInt(10);
                y = RANDOM.nextInt(10);
            }
            return new FieldCoordinate(x, y);
        } else {
            return player.getFurtherCoordinatesToHit().pop();
        }
    }

    public Coordinate parseTurn(String turn) {
        String[] turnParts = turn.split("-");
        return new FieldCoordinate(Integer.parseInt(turnParts[0]), Integer.parseInt(turnParts[1]));
    }

    private boolean isTurnSuccessful(Player player, Coordinate coordinate) {
        return player.getShips().stream().anyMatch(ship -> ship.getShipCoordinates().stream().anyMatch(shipCoordinate -> shipCoordinate.getX() == coordinate.getX() && shipCoordinate.getY() == coordinate.getY()));
    }

    private void processFailedPlayerTurn(Player player, Coordinate coordinate) {
        player.setGameField(coordinate.getX(), coordinate.getY(), PAST_TURN);
    }

    private void createFurtherHitStrategy(Player player, Coordinate coordinate) {
        if(player.getHit() == Hit.ZERO) {
            player.setHit(Hit.FIRST);
            player.setFirstHitCoordinate(coordinate);
            player.clearFurtherCoordinatesToHit();
            player.buildFurtherCoordinatesToHit(coordinate);
        } else if(player.getHit() == Hit.FIRST) {
            player.setHit(Hit.SECOND);
            player.clearFurtherCoordinatesToHit();
            player.buildFurtherCoordinatesToHit(coordinate);
        } else if(player.getHit() == Hit.SECOND) {
            player.clearFurtherCoordinatesToHit();
            player.buildFurtherCoordinatesToHit(coordinate);
        }
    }

    private void processSuccessfulPlayerTurn(Player player, Coordinate coordinate) {
        List<Ship> ships = new ArrayList<>(player.getShips());
        createFurtherHitStrategy(player, coordinate);
        ships.forEach(ship -> {
            Optional<ShipCoordinate> optionalShipCoordinate = ship.getShipCoordinates().stream().filter(shipCoordinate -> shipCoordinate.getX() == coordinate.getX() && shipCoordinate.getY() == coordinate.getY()).findFirst();
            if (optionalShipCoordinate.isPresent()) {
                ShipCoordinate shipCoordinate = optionalShipCoordinate.get();
                ship.getShipCoordinates().remove(shipCoordinate);
                player.setGameField(coordinate.getX(), coordinate.getY(), GOOD_TURN);
            }
            if(ship.getShipCoordinates().isEmpty()) {
                player.setShips(player.getShips().stream().filter(shipElem -> !shipElem.equals(ship)).collect(Collectors.toList()));
                ship.getBoundsCoordinates().forEach(boundCoordinate -> player.setGameField(boundCoordinate.getX(), boundCoordinate.getY(), PAST_TURN));
                player.setHit(Hit.ZERO);
                player.clearFurtherCoordinatesToHit();
            }
        });
    }
}
