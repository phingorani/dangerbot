package com.waffle.dangerbot.utilService;

import org.javacord.api.event.message.MessageCreateEvent;


public class BotUtilService {

    public static Boolean validateBotRollCommand(MessageCreateEvent event, String command) {
        if (event.getMessageContent().startsWith("!"+command)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

}
