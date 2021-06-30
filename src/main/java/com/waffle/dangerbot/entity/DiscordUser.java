package com.waffle.dangerbot.entity;

import javax.persistence.*;

@Entity
@Table(name = "discord_user")
public class DiscordUser {
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getDiscordId() {
        return discordId;
    }

    public void setDiscordId(Long discordId) {
        this.discordId = discordId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "discord_id")
    private Long discordId;

    public DiscordUser(Long userId, String displayName, Long discordId) {
        this.userId = userId;
        this.displayName = displayName;
        this.discordId = discordId;
    }

    public DiscordUser() {

    }
}
