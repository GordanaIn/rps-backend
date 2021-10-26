package com.example.rpsbackend.auth;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TokenController {

    TokenService tokenService;

    @GetMapping("/token")
    public String createNewToken() {
        return tokenService.createToken()
                .getId();
    }
}
