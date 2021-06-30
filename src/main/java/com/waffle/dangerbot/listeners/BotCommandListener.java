package com.waffle.dangerbot.listeners;


import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.beans.factory.annotation.Value;

public class BotCommandListener implements MessageCreateListener {

    @Value("${info.build.version}")
    String versionNum;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(event.getMessage().getContent().equalsIgnoreCase("!version")) {
            event.getChannel().sendMessage("Current Version is "+versionNum);
        }
    }
}
