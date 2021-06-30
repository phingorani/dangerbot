package com.waffle.dangerbot.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String displayName;
    private Long discordId;

    public User(Long userId, String displayName, Long discordId) {
        this.userId = userId;
        this.displayName = displayName;
        this.discordId = discordId;
    }

    public User() {

    }
}
