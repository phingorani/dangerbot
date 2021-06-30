package com.waffle.dangerbot.service;

import com.waffle.dangerbot.entity.DiscordUser;
import com.waffle.dangerbot.repository.DiscordUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscordUserService {

    @Autowired
    private final DiscordUserRepository discordUserRepository;

    public DiscordUserService(DiscordUserRepository discordUserRepository) {
        this.discordUserRepository = discordUserRepository;
    }

    public void save(DiscordUser discordUser) {
        discordUserRepository.save(discordUser);
    }

    public DiscordUser findByDiscordId(long id) {
       return discordUserRepository.findByDiscordId(id);
    }
}
