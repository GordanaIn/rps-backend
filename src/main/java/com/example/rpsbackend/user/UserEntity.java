package com.example.rpsbackend.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class UserEntity implements Comparable<UserEntity> {
    @Id
    String id;
    String name;

    @Override
    public int compareTo(UserEntity userEntity) {
        return this.getId().compareTo(userEntity.getId());
    }
}
