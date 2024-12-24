package com.dimasolo.battleship.model.game;

import com.dimasolo.battleship.model.player.Player;

import java.util.UUID;

public class BattleshipGame implements Game {

    private final String id;
    private Player playerOne;
    private Player playerTwo;

    private Player currentActivePlayer;
    private GameState gameState;
    @Override
    public Player getPlayerOne() {
        return playerOne;
    }

    @Override
    public Player getPlayerTwo() {
        return playerTwo;
    }

    @Override
    public Player getPlayerById(String Id) {
        if (this.playerOne.getPlayerId().equals(Id)) {
            return this.playerOne;
        } else {
            return this.playerTwo;
        }
    }

    @Override
    public Player getCurrentActivePlayer() {
        return this.currentActivePlayer;
    }

    @Override
    public void setCurrentActivePlayer(Player player) {
        this.currentActivePlayer = player;
    }

    @Override
    public String getGameState() {
        return this.gameState.toString();
    }

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }


    public BattleshipGame(Player playerOne, Player playerTwo) {
        this.id = UUID.randomUUID().toString();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.currentActivePlayer = playerTwo;
        this.gameState = GameState.CREATED;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
