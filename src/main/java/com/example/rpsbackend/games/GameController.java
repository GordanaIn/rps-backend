package com.example.rpsbackend.games;

import com.example.rpsbackend.auth.Token;
import com.example.rpsbackend.auth.TokenService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/games")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GameController {

    GameService gameService;
    TokenService tokenService;

    @GetMapping("/start")
    //This method starts the game and is connected to GameService.
    public Game startGame(@RequestHeader(value = "token", required = false) String tokenString) {
        Token token = tokenService.getTokenByTokenString(tokenString);
        return gameService.startGame(token)
                .map(this::toGame)
                .orElse(null);
    }

    @GetMapping("join/{gameId}")
    //With this method you can join a Game and is connected to GameService.
    public Game joinGame(@PathVariable String gameId,
                         @RequestHeader(value = "token", required = false) String tokenString) {
        Token token = tokenService.getTokenByTokenString(tokenString);
        return gameService.joinGame(gameId, token.getId())
                .map(this::toGame)
                .orElse(null);
    }

    @GetMapping("/status")
    //With this method you can see the Status of the current Game. Is connected to GameService.
    public Game gameStatus(@RequestHeader(value = "token", required = false) String tokenString) {
        Token token = tokenService.getTokenByTokenString(tokenString);
        return gameService.getGameById(token.getGameId())
                .map(this::toGame)
                .orElse(null);
    }

    @GetMapping()
    //With this method you can see all the open games with the status OPEN. And is connected to GameService.
    public List< Game > gameList(
            @RequestHeader(value = "token", required = false) String tokenString) {
        Token token = tokenService.getTokenByTokenString(tokenString);
        return gameService.all()
                .filter(game -> game.getGame().equals(GameStatus.OPEN))
                .map(this::toGame)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    //With this method you can find the info of the game by gameId.
    public Game getGameInfo(@PathVariable String id) {
        return gameService.getGameById(id)
                .map(this::toGame)
                .orElse(null);
    }


    @GetMapping("/move/{sign}")
    //With this method the players can choose their move. And is connected to GameService.
    public Game makeMove(@RequestHeader(value = "token", required = false) String tokenString,@PathVariable String sign) {
        Token token = tokenService.getTokenByTokenString(tokenString);
        if (sign.toUpperCase().equals(Move.ROCK.toString()) || sign.toUpperCase().equals(Move.SCISSORS.toString()) || sign.toUpperCase().equals(Move.PAPER.toString())) {
            return gameService.makeMove(sign.toUpperCase(), token)
                    .map(this::toGame)
                    .orElse(null);
        }
        return null;
    }
    // This method is Go toGame. And is connected to GameEntity.
    private Game toGame(GameEntity gameEntity) {
        return new Game(
                gameEntity.getId(),
                gameEntity.getName(),
                gameEntity.getMove(),
                gameEntity.getGame(),
                gameEntity.getOpponentName(),
                gameEntity.getOpponentMove());
    }
}
