package com.waffle.dangerbot.listeners;

import com.waffle.dangerbot.constants.BotCommandsConstant;
import com.waffle.dangerbot.entity.User;
import com.waffle.dangerbot.service.UserService;
import com.waffle.dangerbot.utilService.BotUtilService;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
public class RollListener implements MessageCreateListener {

    Boolean isBotRollCommand = Boolean.FALSE;

    Long channelId = 857362959109586984L;

    @Autowired
    private UserService userService;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        validateBotRollCommand(event);
        if (!isBotRollCommand) {
            return;
        }
        Integer upperLimit = extractUpperLimit(event.getMessageContent());

        String result = randomNumberGenerator(upperLimit).toString();


        if (result.equals("1")) {
            sendLoserMessage(event);
        } else {
            sendRegularMessage(event, result, upperLimit.toString());
        }

    }

    private Integer extractUpperLimit(String messageContent) {
        List<String> splitStrings = Arrays.asList(messageContent.split(" "));

        if (splitStrings.isEmpty() || splitStrings.size() == 1 || !splitStrings.get(1).matches("^[0-9]+$")) {
            return 100;
        }

        String numberOnly = messageContent.replaceAll("[^0-9]", "");
        return Integer.parseInt(numberOnly);
    }

    private void validateBotRollCommand(MessageCreateEvent event) {
        if (BotUtilService.validateBotRollCommand(event, BotCommandsConstant.ROLL) && channelId.longValue() == event.getChannel().getId()) {
            isBotRollCommand = Boolean.TRUE;
        } else {
            isBotRollCommand = Boolean.FALSE;
        }
    }

    private Integer randomNumberGenerator(Integer upperLimit) {
        Random r = new Random();
        return r.nextInt(upperLimit) + 1;
    }

    private void sendLoserMessage(MessageCreateEvent event) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.append("<@" + event.getMessageAuthor().getId() + ">! You rolled a 1! You lose!");

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setImage("https://media1.tenor.com/images/7066494e5810e5a84d68d5696004eec4/tenor.gif?itemid=7465431");
        messageBuilder.setEmbed(embedBuilder);
        messageBuilder.send(event.getChannel());
    }

    private void sendRegularMessage(MessageCreateEvent event, String result, String upperLimit) {
        System.out.println("User: " + event.getMessageAuthor().getDisplayName() + " " + event.getMessageAuthor().getId());
        event.getChannel().sendMessage("<@" + event.getMessageAuthor().getId() + "> rolled a " + result + " out of " + upperLimit);

        if(userService == null) {
            System.out.println("User Repo is null");
        }
        else {
            Optional<User> exists = Optional.ofNullable(userService.findByDiscordId(event.getMessageAuthor().getId()));
            if (exists.isEmpty()) {
                User userToSave = new User(null, event.getMessageAuthor().getDisplayName(), event.getMessageAuthor().getId());
                userService.save(userToSave);
            }
        }
    }
}
