package com.waffle.dangerbot.listeners;

import com.waffle.dangerbot.entity.DiscordUser;
import com.waffle.dangerbot.entity.GameSession;
import com.waffle.dangerbot.service.DiscordUserService;
import com.waffle.dangerbot.service.GameSessionService;
import com.waffle.dangerbot.utilService.BotUtilService;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class RollListener implements MessageCreateListener {

    Long channelId = 857362959109586984L;

    @Autowired
    private DiscordUserService discordUserService;

    @Autowired
    private GameSessionService gameSessionService;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        if (!BotUtilService.isValidateBotCommand(event) || !BotUtilService.isCorrectChannel(event, channelId)) {
            return;
        }

        if (BotUtilService.isChallengeCommand(event)) {
            createChallengeSession(event);
        } else if (BotUtilService.isDeleteChallengeCommand(event)) {
            deleteRollSession(event);
        } else if (BotUtilService.isAcceptChallengeCommand(event)) {
            acceptChallengeSession(event);
        } else if (BotUtilService.isRollCommand(event)) {
            createRollSession(event);
        }
    }

    private void deleteRollSession(MessageCreateEvent event) {
        Optional<GameSession> exists = Optional.ofNullable(gameSessionService.findByChallengerId(event.getMessageAuthor().getId()));

        if (exists.isPresent()) {
            gameSessionService.delete(exists.get());
            event.getChannel().sendMessage("<@" + event.getMessageAuthor().getId() + "> challenge with <@"+exists.get().getChallengedId() +"> deleted Quitter!");
        }
    }

    private void acceptChallengeSession(MessageCreateEvent event) {
        Optional<GameSession> exists = Optional.ofNullable(gameSessionService.findByChallengedId(event.getMessageAuthor().getId()));
        if(exists.isPresent()) {
            GameSession gameSessionToUpdate = exists.get();
            gameSessionToUpdate.setAcceptedInd(Boolean.TRUE);
            gameSessionService.save(gameSessionToUpdate);
            event.getChannel().sendMessage("Challenge Accepted! <@" + gameSessionToUpdate.getChallengerId() + "> and <@"+exists.get().getChallengedId() +">, get ready to roll! May the best Kegz win!");
        }
    }

    private void createRollSession(MessageCreateEvent event) {
        Optional<GameSession> exists = Optional.ofNullable(gameSessionService.findGameSessionByChallengedIdOrChallengerId(event.getMessageAuthor().getId()));

        if(exists.isEmpty()) {
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

    private void createChallengeSession(MessageCreateEvent event) {

        Optional<GameSession> exists = Optional.ofNullable(gameSessionService.findByChallengerId(event.getMessageAuthor().getId()));
        if (exists.isPresent()) {
            event.getChannel().sendMessage("<@" + event.getMessageAuthor().getId() + "> You already have an active challenge with <@" + exists.get().getChallengedId() + "> If you'd like to quit type command !delete");
            return;
        }
        event.getChannel().sendMessage("<@" + event.getMessageAuthor().getId() + "> You challenged <@" + event.getMessage().getMentionedUsers().get(0).getId() + "> for " + event.getMessageContent().split(" ")[2] + " gold!");
        GameSession gameSessionToSave = new GameSession();
        gameSessionToSave.setChallengerId(event.getMessageAuthor().getId());
        gameSessionToSave.setChallengedId(event.getMessage().getMentionedUsers().get(0).getId());
        gameSessionToSave.setBetAmount(Integer.parseInt(event.getMessageContent().split(" ")[2]));
        gameSessionToSave.setAcceptedInd(Boolean.FALSE);
        gameSessionService.save(gameSessionToSave);
    }

    private Integer extractUpperLimit(String messageContent) {
        List<String> splitStrings = Arrays.asList(messageContent.split(" "));

        if (splitStrings.isEmpty() || splitStrings.size() == 1 || !splitStrings.get(1).matches("^[0-9]+$")) {
            return 100;
        }

        String numberOnly = messageContent.replaceAll("[^0-9]", "");
        return Integer.parseInt(numberOnly);
    }

    private Integer randomNumberGenerator(Integer upperLimit) {
        Random r = new Random();
        return r.nextInt(upperLimit) + 1;
    }

    private void sendLoserMessage(MessageCreateEvent event) {
        Optional<GameSession> exists = Optional.ofNullable(gameSessionService.findGameSessionByChallengedIdOrChallengerId(event.getMessageAuthor().getId()));

        gameSessionService.delete(exists.get());

        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.append("<@" + event.getMessageAuthor().getId() + ">! You rolled a 1! You lose!");

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setImage("https://media1.tenor.com/images/7066494e5810e5a84d68d5696004eec4/tenor.gif?itemid=7465431");
        messageBuilder.setEmbed(embedBuilder);
        messageBuilder.send(event.getChannel());
    }

    private void sendRegularMessage(MessageCreateEvent event, String result, String upperLimit) {
        event.getChannel().sendMessage("<@" + event.getMessageAuthor().getId() + "> rolled a " + result + " out of " + upperLimit);

        Optional<DiscordUser> exists = Optional.ofNullable(discordUserService.findByDiscordId(event.getMessageAuthor().getId()));
        if (exists.isEmpty()) {
            DiscordUser discordUserToSave = new DiscordUser(event.getMessageAuthor().getDisplayName(), event.getMessageAuthor().getId());
            discordUserService.save(discordUserToSave);
        }
    }
}
