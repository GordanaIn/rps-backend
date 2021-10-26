package com.example.rpsbackend.auth;

import com.example.rpsbackend.user.UserEntity;
import com.example.rpsbackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;
    static boolean isAlreadyCreatedPlayerOne = false;

    //This logic uses to create a new Token and is connected with the method in TokenController.
    public TokenEntity createToken() {
        TokenEntity tokenEntity;
        if(!isAlreadyCreatedPlayerOne) {
            tokenEntity = createTockenEntity("Player1");
        } else {
            tokenEntity = createTockenEntity("Player2");
            isAlreadyCreatedPlayerOne = false;
        }
        return tokenEntity;
    }

    private TokenEntity createTockenEntity (String name) {
        isAlreadyCreatedPlayerOne = true;
        TokenEntity token = new TokenEntity(UUID.randomUUID().toString(), UUID.randomUUID().toString(), name);
        TokenEntity savedToken = tokenRepository.save(token);
        UserEntity user = new UserEntity(savedToken.getId(), "");
        savedToken.setUserEntity(user);
        userRepository.save(user);
        tokenRepository.save(savedToken); //Creates the token
        return token;
    }

    //This fetches the token
    public Token getTokenByTokenString(String tokenString) {
        return tokenRepository.findById(tokenString)
                .map(this::toToken)
                .orElse(null);
    }

    private Token toToken(TokenEntity tokenEntity) {
        return new Token(tokenEntity.getId(), tokenEntity.getGameId(), tokenEntity.getName());
    }
}
