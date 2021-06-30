package com.waffle.dangerbot.utilService;

import com.waffle.dangerbot.constants.BotCommandsConstant;
import com.waffle.dangerbot.constants.UserIdConstants;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Arrays;
import java.util.List;


public class BotUtilService {

    public static Boolean isValidateBotCommand(MessageCreateEvent event) {
        return event.getMessageContent().toUpperCase().startsWith("!") && !event.getMessageAuthor().getDisplayName().equals("DangerBot");
    }

    public static Boolean isRollCommand(MessageCreateEvent event) {
        return event.getMessageContent().toUpperCase().startsWith("!"+BotCommandsConstant.ROLL.toUpperCase());
    }

    public static Boolean isChallengeCommand(MessageCreateEvent event) {
        List<String> strings = Arrays.asList(event.getMessageContent().split(" "));

        if (strings.isEmpty() || strings.size() < 3) {
            return Boolean.FALSE;
        }
        if(!strings.get(0).startsWith("!") || (!strings.get(1).startsWith("<@")&& !strings.get(1).endsWith(">")) || !StringUtils.isNumeric(strings.get(2))) {
            return Boolean.FALSE;
        }
        return event.getMessageContent().split(" ")[0].equalsIgnoreCase("!"+BotCommandsConstant.CHALLENGE);
    }

    public static Boolean isDeleteChallengeCommand(MessageCreateEvent event) {
        return event.getMessageContent().equalsIgnoreCase("!"+BotCommandsConstant.DECLINE_CHALLENGE);
    }

    public static Boolean isCorrectChannel(MessageCreateEvent event, Long channelId) {
        return event.getChannel().getId() == channelId;
    }

    public static boolean isAcceptChallengeCommand(MessageCreateEvent event) {
        return event.getMessageContent().equalsIgnoreCase("!"+BotCommandsConstant.ACCEPT_CHALLENGE);
    }

    public static boolean isAdmin(MessageCreateEvent event) {
        return event.getMessageAuthor().getId() == UserIdConstants.WAFFLE;
    }

    public static boolean isUpdateUsers(MessageCreateEvent event) {
        return event.getMessageContent().replace("!","").equalsIgnoreCase(BotCommandsConstant.UPDATE_USER_LIST);
    }
}
