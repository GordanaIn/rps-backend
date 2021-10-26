package com.example.rpsbackend.games;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Game { // DTO används bara på utsidan
    String id;
    String name; // Spelarens namn om det har blivit angivet
    Move move;
    GameStatus game;
    String opponentName;
    Move opponentMove;
}
