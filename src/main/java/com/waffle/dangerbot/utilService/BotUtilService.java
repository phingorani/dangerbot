package com.waffle.dangerbot.utilService;

import com.waffle.dangerbot.constants.BotCommandsConstant;
import org.javacord.api.event.message.MessageCreateEvent;


public class BotUtilService {

    public static Boolean isValidateBotCommand(MessageCreateEvent event) {
        return event.getMessageContent().toUpperCase().startsWith("!") && !event.getMessageAuthor().getDisplayName().equals("DangerBot");
    }

    public static Boolean isRollCommand(MessageCreateEvent event) {
        return event.getMessageContent().split(" ")[1].equalsIgnoreCase(BotCommandsConstant.ROLL);
    }

    public static Boolean isChallengeCommand(MessageCreateEvent event) {
        return event.getMessageContent().split(" ")[1].equalsIgnoreCase(BotCommandsConstant.CHALLENGE);
    }

    public static Boolean isDeleteCommand(MessageCreateEvent event) {
        return event.getMessageContent().split(" ")[1].equalsIgnoreCase(BotCommandsConstant.DELETE);
    }

    public static Boolean isCorrectChannel(MessageCreateEvent event, Long channelId) {
        return event.getChannel().getId() == channelId;
    }

}
