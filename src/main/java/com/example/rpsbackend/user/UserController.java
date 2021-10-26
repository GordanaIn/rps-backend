package com.example.rpsbackend.user;

import com.example.rpsbackend.auth.Token;
import com.example.rpsbackend.auth.TokenService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class UserController {
    UserService userService;
    TokenService tokenService;

    @PostMapping("/name")
    //This sets the name of the player/user.
    public User setName(@RequestBody CreateUser createUser,
                       @RequestHeader(value = "token", required = false) String tokenString) {
        Token token = tokenService.getTokenByTokenString(tokenString);
        return userService.setName(createUser,token)
                .map(this::toUser)
                .orElse(null);
    }
    //This method generates the new object.
    private User toUser(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getName());
    }
}
