package com.waffle.dangerbot.listeners;


import com.waffle.dangerbot.entity.DiscordUser;
import com.waffle.dangerbot.service.DiscordUserService;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BotCommandListener implements MessageCreateListener {

    @Autowired
    private DiscordUserService discordUserService;

    @Value("${info.build.version}")
    String versionNum;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(event.getMessage().getContent().equalsIgnoreCase("!version")) {
            event.getChannel().sendMessage("Current Version is "+versionNum);
        }
        Optional<DiscordUser> exists = Optional.ofNullable(discordUserService.findByDiscordId(event.getMessageAuthor().getId()));
        if (exists.isEmpty()) {
            DiscordUser discordUserToSave = new DiscordUser(event.getMessageAuthor().getDisplayName(), event.getMessageAuthor().getId());
            discordUserService.save(discordUserToSave);
        }
    }
}
