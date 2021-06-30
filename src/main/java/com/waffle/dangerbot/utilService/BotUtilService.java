package com.waffle.dangerbot.utilService;

import com.waffle.dangerbot.constants.BotCommandsConstant;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Arrays;
import java.util.List;


public class BotUtilService {

    public static Boolean isValidateBotCommand(MessageCreateEvent event) {
        return event.getMessageContent().toUpperCase().startsWith("!") && !event.getMessageAuthor().getDisplayName().equals("DangerBot");
    }

    public static Boolean isRollCommand(MessageCreateEvent event) {
        return event.getMessageContent().equalsIgnoreCase("!"+BotCommandsConstant.ROLL);
    }

    public static Boolean isChallengeCommand(MessageCreateEvent event) {
        List<String> strings = Arrays.asList(event.getMessageContent().split(" "));

        if (strings.isEmpty() || strings.size() < 3) {
            return Boolean.FALSE;
        }
        return event.getMessageContent().split(" ")[1].equalsIgnoreCase(BotCommandsConstant.CHALLENGE);
    }

    public static Boolean isDeleteCommand(MessageCreateEvent event) {
        return event.getMessageContent().split(" ")[1].equalsIgnoreCase(BotCommandsConstant.DELETE);
    }

    public static Boolean isCorrectChannel(MessageCreateEvent event, Long channelId) {
        return event.getChannel().getId() == channelId;
    }

}
