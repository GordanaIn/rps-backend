package com.example.rpsbackend.auth;

import com.example.rpsbackend.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "token")
public class TokenEntity {
    @Id String id;
    String gameId;
    String name;

    @OneToOne
    UserEntity userEntity;

    public TokenEntity(String id, String gameId, String name) {
        this.id = id;
        this.gameId = gameId;
        this.name = name;
    }
}
