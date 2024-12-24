package com.dimasolo.battleship.model.game;

import com.dimasolo.battleship.model.player.Player;

public interface Game {
    String getId();
    Player getPlayerOne();
    Player getPlayerTwo();

    Player getPlayerById(String Id);

    Player getCurrentActivePlayer();
    void setCurrentActivePlayer(Player player);

    String getGameState();

    void setGameState(GameState gameState);
}
