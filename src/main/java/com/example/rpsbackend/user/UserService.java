package com.example.rpsbackend.user;

import com.example.rpsbackend.auth.Token;
import com.example.rpsbackend.games.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    GameRepository gameRepository;

    @Autowired
    public UserService(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    //logic for creating user
    public Optional< UserEntity > setName(CreateUser createUser, Token token) {
        Optional< UserEntity > user = userRepository.findById(token.getId());
        user.ifPresent(userEntity -> userEntity.setName(createUser.getName()));
        UserEntity save = userRepository.save(user.get());
        return Optional.of(save);
    }
}
