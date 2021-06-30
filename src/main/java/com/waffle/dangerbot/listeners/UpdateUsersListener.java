package com.waffle.dangerbot.listeners;

import com.waffle.dangerbot.entity.DiscordUser;
import com.waffle.dangerbot.repository.DiscordUserRepository;
import com.waffle.dangerbot.utilService.BotUtilService;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateUsersListener implements MessageCreateListener {

    @Autowired
    DiscordUserRepository discordUserRepository;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (BotUtilService.isValidateBotCommand(event)) {
            if (BotUtilService.isAdmin(event) && BotUtilService.isUpdateUsers(event)) {
                event.getServer().get().getMembers().forEach(user -> {
                    if (discordUserRepository.existsById(user.getId())) {
                        DiscordUser discordUser = new DiscordUser();
                        discordUser.setDisplayName(user.getName());
                        discordUser.setDiscordId(user.getId());
                        discordUserRepository.save(discordUser);
                    }
                });
            }
        }
    }
}
