package com.waffle.dangerbot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "discord_server")
@Entity
public class DiscordServer {
    @Id
    @Column(name = "server_id", nullable = false)
    private Integer id;

    @Column(name = "server_name")
    private Integer serverName;

    public Integer getServerName() {
        return serverName;
    }

    public void setServerName(Integer serverName) {
        this.serverName = serverName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}