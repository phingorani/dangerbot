package com.waffle.dangerbot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "discord_channel")
public class DiscordChannel {

    @Id
    @Column(name = "discord_channel_id")
    private Long discordChannelId;

    @Column(name = "discord_channel_name")
    private String discordChannelName;

    @Column(name = "text_channel_ind")
    private Boolean textChannelInd = Boolean.FALSE;

    @Column(name = "voice_channel_ind")
    private Boolean voiceChannelInd = Boolean.FALSE;

    public Long getDiscordChannelId() {
        return discordChannelId;
    }

    public void setDiscordChannelId(Long discordChannelId) {
        this.discordChannelId = discordChannelId;
    }

    public String getDiscordChannelName() {
        return discordChannelName;
    }

    public void setDiscordChannelName(String discordChannelName) {
        this.discordChannelName = discordChannelName;
    }

    public Boolean getTextChannelInd() {
        return textChannelInd;
    }

    public void setTextChannelInd(Boolean textChannelInd) {
        this.textChannelInd = textChannelInd;
    }

    public Boolean getVoiceChannelInd() {
        return voiceChannelInd;
    }

    public void setVoiceChannelInd(Boolean voiceChannelInd) {
        this.voiceChannelInd = voiceChannelInd;
    }
}
