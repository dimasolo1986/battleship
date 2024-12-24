package com.dimasolo.battleship.builder;

import com.dimasolo.battleship.model.game.BattleshipGame;
import com.dimasolo.battleship.model.game.Game;
import com.dimasolo.battleship.model.player.BattleshipPlayer;
import com.dimasolo.battleship.model.player.Player;
import com.dimasolo.battleship.model.player.PlayerState;
import com.dimasolo.battleship.model.player.PlayerType;
import org.springframework.stereotype.Component;

@Component
public class GameBuilder {

    public Game buildGame() throws Exception {
        Player playerOne = new BattleshipPlayer(new FleetBuilder(new ShipBuilder()).buildShipFleet(), PlayerType.USER, PlayerState.ACTIVE);
        Player playerTwo = new BattleshipPlayer(new FleetBuilder(new ShipBuilder()).buildShipFleet(), PlayerType.COMPUTER, PlayerState.ACTIVE);
        playerOne.setRivalId(playerTwo.getPlayerId());
        playerOne.setRival(playerTwo);
        playerTwo.setRivalId(playerOne.getPlayerId());
        playerTwo.setRival(playerOne);
        Game game = new BattleshipGame(playerOne, playerTwo);
        game.setCurrentActivePlayer(playerOne);
        return game;
    }
}
