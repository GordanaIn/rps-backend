package com.example.rpsbackend.games;


import com.example.rpsbackend.auth.Token;
import com.example.rpsbackend.auth.TokenEntity;
import com.example.rpsbackend.auth.TokenRepository;
import com.example.rpsbackend.user.UserEntity;
import com.example.rpsbackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class GameService {

    GameRepository gameRepository;
    TokenRepository tokenRepository;
    UserRepository userRepository;

    @Autowired
    public GameService(GameRepository gameRepository, TokenRepository tokenRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    //This fetches all the games
    public Stream< GameEntity > all() {
        return gameRepository.findAll().stream();
    }

    //This method finds the game id. Connected to GameController.
    public Optional< GameEntity > getGameById(String id) {
        return gameRepository.findById(id);
    }

    public Optional< GameEntity > startGame(Token token) {
        UserEntity userEntity = userRepository.findById(token.getId()).get();
        List< UserEntity > users = new ArrayList<>();
        users.add(userEntity);
        GameEntity game = new GameEntity(token.getGameId(), userEntity.getName(), null, GameStatus.OPEN, "", null, users);
        gameRepository.save(game);
        return Optional.of(game);
    }

    // This is the logic to join the game.
    public Optional< GameEntity > joinGame(String gameId, String userId) {
        TokenEntity tokenEntity = tokenRepository.findById(userId).get();
        Optional< GameEntity > joinToGame = gameRepository.findById(gameId);
        if (joinToGame.get().game.equals(GameStatus.OPEN)) {
            UserEntity opponentUser = userRepository.findById(userId).get();
            joinToGame.get().getUsers().add(opponentUser);
            joinToGame.get().setOpponentName(opponentUser.getName());
            joinToGame.get().setGame(GameStatus.ACTIVE);
            gameRepository.save(joinToGame.get());
            tokenEntity.setGameId(gameId);
            tokenRepository.save(tokenEntity);
        }
        return joinToGame;
    }

    //With this logic the player/user can use to make a move.
    public Optional< GameEntity > makeMove(String move, Token token) {
        Optional< GameEntity > currentGame = gameRepository.findById(token.getGameId());
        TokenEntity tokenEntity = tokenRepository.findById(token.getId()).get();

        if (tokenEntity.getName().equals("Player1")) {
            currentGame.get().setMove(Move.valueOf(move));
        } else {
            currentGame.get().setOpponentMove(Move.valueOf(move));
        }

        Move userMove = currentGame.get().getMove();
        Move opponentMove = currentGame.get().getOpponentMove();

        if (userMove == null || opponentMove == null) {
            return Optional.of(gameRepository.save(currentGame.get()));
        }

        if (userMove.equals(opponentMove)) {
            currentGame.get().setGame(GameStatus.DRAW);
        } else if (userMove.equals(Move.ROCK) && opponentMove.equals(Move.PAPER) ||
                userMove.equals(Move.PAPER) && opponentMove.equals(Move.SCISSORS) ||
                userMove.equals(Move.SCISSORS) && opponentMove.equals(Move.ROCK)) {
            currentGame.get().setGame(GameStatus.LOSE);
        } else {
            currentGame.get().setGame(GameStatus.WIN);
        }
        return Optional.of(gameRepository.save(currentGame.get()));
    }

    //This saves the current Game
    public GameEntity saveGame(Optional< GameEntity > gameEntityResult) {
        return gameRepository.save(gameEntityResult.get());
    }
}
