package com.example.georgestore.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_tokens")
@Getter @Setter
public class UserTokenEntity {

    @Id
    private String username;

    @Column(nullable = false)
    private String token;
}
