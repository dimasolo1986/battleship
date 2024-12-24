package com.dimasolo.battleship.controller;

import com.dimasolo.battleship.model.Coordinate;
import com.dimasolo.battleship.model.game.Game;
import com.dimasolo.battleship.model.game.GameState;
import com.dimasolo.battleship.model.player.Player;
import com.dimasolo.battleship.service.BattleshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
public class BattleshipController {

    private final BattleshipService battleshipService;
    private final HashMap<String, Game> games;

    @Autowired
    public BattleshipController(BattleshipService battleshipService) {
        this.battleshipService = battleshipService;
        this.games = new HashMap<>();
    }

    @RequestMapping("/")
    public String home(Model model) throws Exception {
        Game battleshipGame = battleshipService.createBattleshipGame();
        this.games.put(battleshipGame.getId(), battleshipGame);
        model.addAttribute("game", battleshipGame);
        return "battleship";
    }

    @RequestMapping("/startGame/{gameId}")
    public String startGame(@PathVariable(value = "gameId") String gameId, Model model) throws Exception {
        Game battleshipGame = this.games.containsKey(gameId) ? this.games.get(gameId) : battleshipService.createBattleshipGame();
        battleshipGame.setGameState(GameState.ACTIVE);
        this.games.put(battleshipGame.getId(), battleshipGame);
        model.addAttribute("game", battleshipGame);
        return "battleship";
    }

    @RequestMapping("/finishGame/{gameId}")
    public String finishGame(@PathVariable(value = "gameId") String gameId, Model model) throws Exception {
       this.games.remove(gameId);
       return "redirect:/";
    }

    @RequestMapping("/turn/{gameId}/{playerIdentifier}")
    public String turnGame(@PathVariable(value = "gameId") String gameId, @PathVariable(value = "playerIdentifier") String playerIdentifier, @RequestParam(value = "turn", required = false) String turn,  Model model) throws Exception {
        Game game = this.games.get(gameId);
        Player player = game.getPlayerById(playerIdentifier);
        Coordinate coordinate = battleshipService.parseTurn(turn);
        boolean playerTurn = battleshipService.turn(player, coordinate);
        if (playerTurn) {
            game.setCurrentActivePlayer(game.getPlayerOne());

        } else {
            game.setCurrentActivePlayer(player);
        }
        model.addAttribute("game", game);

        return "battleship";
    }

    @RequestMapping("/turnComputer/{gameId}/{playerIdentifier}")
    public String turnComputerGame(@PathVariable(value = "gameId") String gameId, @PathVariable(value = "playerIdentifier") String playerIdentifier, Model model) throws Exception {
        Game game = this.games.get(gameId);
        Player player = game.getPlayerById(playerIdentifier);
        Coordinate coordinate = battleshipService.generateCoordinate(player);
        boolean playerTurn = battleshipService.turn(player, coordinate);
        if (playerTurn) {
            game.setCurrentActivePlayer(game.getPlayerTwo());
        } else {
            game.setCurrentActivePlayer(player);
        }
        model.addAttribute("game", game);
        return "battleship";
    }
}
