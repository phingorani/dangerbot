package com.waffle.dangerbot.listeners;

import com.waffle.dangerbot.entity.DiscordUser;
import com.waffle.dangerbot.repository.DiscordUserRepository;
import org.javacord.api.event.server.ServerJoinEvent;
import org.javacord.api.listener.server.ServerJoinListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateUsersListener implements ServerJoinListener {

    @Autowired
    DiscordUserRepository discordUserRepository;

    @Override
    public void onServerJoin(ServerJoinEvent event) {
        event.getApi().getCachedUsers().forEach(user -> {
            if(discordUserRepository.existsById(user.getId())) {
                DiscordUser discordUser = new DiscordUser();
                discordUser.setDisplayName(user.getName());
                discordUser.setDiscordId(user.getId());
                discordUserRepository.save(discordUser);
            }
        });
    }
}
