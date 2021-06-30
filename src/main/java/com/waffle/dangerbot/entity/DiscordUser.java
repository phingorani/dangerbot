package com.waffle.dangerbot.entity;

import javax.persistence.*;

@Entity
@Table(name = "discord_user")
public class DiscordUser {

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

    @Column(name = "display_name")
    private String displayName;

    @Id
    @Column(name = "discord_id")
    private Long discordId;

    public DiscordUser(String displayName, Long discordId) {
        this.displayName = displayName;
        this.discordId = discordId;
    }

    public DiscordUser() {

    }
}
