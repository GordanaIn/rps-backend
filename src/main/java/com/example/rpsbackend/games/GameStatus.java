package com.example.rpsbackend.games;

import java.io.Serializable;

public enum GameStatus implements Serializable {
    NONE,
    OPEN,
    ACTIVE,
    WIN,
    LOSE,
    DRAW;
}
