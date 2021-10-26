package com.example.rpsbackend.games;

import com.example.rpsbackend.user.UserEntity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "game")
public class GameEntity {
    @Id String id; // Game id i UUID format
    String name; // Spelarens namn om det har blivit angivet
    @Enumerated(EnumType.STRING)
    Move move; // Spelarens drag om det är utfört
    @Enumerated(EnumType.STRING)
    GameStatus game; // Spelets status [NONE, OPEN,ACTIVE, WIN, LOSE, DRAW]*
    String opponentName;
    @Enumerated(EnumType.STRING)
    Move opponentMove;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<UserEntity> users;
}
