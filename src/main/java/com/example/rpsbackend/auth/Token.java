package com.example.rpsbackend.auth;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)

public class Token {
    String id;
    String gameId;
    String name;

    @JsonCreator
    public Token(
            @JsonProperty("id") String id,
            @JsonProperty("gameId") String gameId,
            @JsonProperty("name") String name) {
        this.id = id;
        this.gameId = gameId;
        this.name = name;
    }
}
