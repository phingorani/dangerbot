package com.waffle.dangerbot.listners;

import com.waffle.dangerbot.utilService.BotUtilService;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class RollListener implements MessageCreateListener {

    Boolean isBotRollCommand = Boolean.FALSE;

    Long channelId = 857362959109586984L;

    String command = "roll";


    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        validateBotRollCommand(event);
        if (!isBotRollCommand) {
            return;
        }
        Integer upperLimit = extractUpperLimit(event.getMessageContent());

        String result = randomNumberGenerator(upperLimit).toString();


        if(result.equals("1")) {
            sendLoserMessage(event);
        }
        else {
            sendRegularMessage(event, result, upperLimit.toString());
        }

    }

    private Integer extractUpperLimit(String messageContent) {
        List<String> splitStrings = Arrays.asList(messageContent.split(" "));

        if(splitStrings.isEmpty() || splitStrings.size() == 1  || !splitStrings.get(1).matches("^[0-9]+$")) {
            return 100;
        }

        String numberOnly = messageContent.replaceAll("[^0-9]", "");
        return Integer.parseInt(numberOnly);
    }

    private void validateBotRollCommand(MessageCreateEvent event) {
        if (BotUtilService.validateBotRollCommand(event, command) && channelId.longValue() == event.getChannel().getId()) {
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
        messageBuilder.append("<@"+event.getMessageAuthor().getId()+">! You rolled a 1! You lose!");

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setImage("https://media1.tenor.com/images/7066494e5810e5a84d68d5696004eec4/tenor.gif?itemid=7465431");
        messageBuilder.setEmbed(embedBuilder);
        messageBuilder.send(event.getChannel());
    }

    private void sendRegularMessage(MessageCreateEvent event, String result, String upperLimit) {
        event.getChannel().sendMessage("<@" + event.getMessageAuthor().getId() + "> rolled a " + result + " out of " + upperLimit);
    }
}
