package com.waffle.dangerbot.listeners;

import com.waffle.dangerbot.entity.GameSession;
import com.waffle.dangerbot.service.GameSessionService;
import com.waffle.dangerbot.utilService.BotUtilService;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

@Component
public class RollListener implements MessageCreateListener {

    Long channelId = 857362959109586984L;

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
            declineChallengeSession(event);
        } else if (BotUtilService.isAcceptChallengeCommand(event)) {
            acceptChallengeSession(event);
        } else if (BotUtilService.isRollCommand(event)) {
            createRollSession(event);
        }
    }

    private void declineChallengeSession(MessageCreateEvent event) {
        Optional<GameSession> exists = Optional.ofNullable(gameSessionService.findGameSessionByChallengedIdOrChallengerId(event.getMessageAuthor().getId()));
        Long winnerId;

        if(event.getMessageAuthor().getId()==exists.get().getChallengerId()) {
            winnerId = exists.get().getChallengedId();
        }
        else {
            winnerId = exists.get().getChallengerId();
        }
        gameSessionService.delete(exists.get());
        event.getChannel().sendMessage("<@" + event.getMessageAuthor().getId() + "> challenge with <@" + winnerId + "> deleted Quitter!");
    }

    private void acceptChallengeSession(MessageCreateEvent event) {
        Optional<GameSession> exists = Optional.ofNullable(gameSessionService.findByChallengedId(event.getMessageAuthor().getId()));
        if (exists.isPresent()) {
            GameSession gameSessionToUpdate = exists.get();
            gameSessionToUpdate.setAcceptedInd(Boolean.TRUE);
            gameSessionService.save(gameSessionToUpdate);
            event.getChannel().sendMessage("Challenge Accepted! <@" + gameSessionToUpdate.getChallengerId() + "> and <@" + exists.get().getChallengedId() + ">, get ready to roll! May the best Kegz win!");
        }
    }

    private void createRollSession(MessageCreateEvent event) {

        Optional<GameSession> exists = Optional.ofNullable(gameSessionService.findGameSessionByChallengedIdOrChallengerId(event.getMessageAuthor().getId()));

        if (!exists.isPresent()) {
            return;
        }

        GameSession gameSession = exists.get();

        if(!gameSession.getAcceptedInd()) {
            return;
        }

        if ((gameSession.getChallengerTurn() && gameSession.getChallengerId() == event.getMessageAuthor().getId()) ||
                (gameSession.getChallengedTurn() && gameSession.getChallengedId() == event.getMessageAuthor().getId())) {

            Integer upperLimit;
            if(gameSession.getRollLimit() == null) {
                upperLimit = 100;
            } else{
                upperLimit = gameSession.getRollLimit();
            }

            String result = randomNumberGenerator(upperLimit).toString();

            if (result.equals("1")) {
                sendLoserMessage(event);
            } else {
                gameSession.setChallengedTurn(!gameSession.getChallengedTurn());
                gameSession.setChallengerTurn(!gameSession.getChallengerTurn());
                gameSession.setRollLimit(Integer.parseInt(result));
                gameSessionService.save(gameSession);
                sendRegularMessage(event, result, upperLimit.toString());
            }
        }
    }

    private void createChallengeSession(MessageCreateEvent event) {

        Optional<GameSession> exists = Optional.ofNullable(gameSessionService.findGameSessionByChallengedIdOrChallengerId(event.getMessageAuthor().getId()));
        if (exists.isPresent()) {
            Long winnerId;

            if(event.getMessageAuthor().getId()==exists.get().getChallengerId()) {
                winnerId = exists.get().getChallengedId();
            }
            else {
                winnerId = exists.get().getChallengerId();
            }
            event.getChannel().sendMessage("<@" + event.getMessageAuthor().getId() + "> You already have an active challenge with <@" + winnerId + "> If you'd like to quit type command !decline");
            return;
        }
        event.getChannel().sendMessage("<@" + event.getMessageAuthor().getId() + "> You challenged <@" + event.getMessage().getMentionedUsers().get(0).getId() + "> for " + event.getMessageContent().split(" ")[2] + " gold!");
        GameSession gameSessionToSave = new GameSession();
        gameSessionToSave.setChallengerId(event.getMessageAuthor().getId());
        gameSessionToSave.setChallengedId(event.getMessage().getMentionedUsers().get(0).getId());
        gameSessionToSave.setBetAmount(Integer.parseInt(event.getMessageContent().split(" ")[2]));
        gameSessionToSave.setAcceptedInd(Boolean.FALSE);
        gameSessionToSave.setChallengerTurn(Boolean.TRUE);
        gameSessionToSave.setChallengedTurn(Boolean.FALSE);
        gameSessionService.save(gameSessionToSave);
    }

    private Integer randomNumberGenerator(Integer upperLimit) {
        Random r = new Random();
        return r.nextInt(upperLimit) + 1;
    }

    private void sendLoserMessage(MessageCreateEvent event) {
        Optional<GameSession> exists = Optional.ofNullable(gameSessionService.findGameSessionByChallengedIdOrChallengerId(event.getMessageAuthor().getId()));

        Long winnerId;

        if(event.getMessageAuthor().getId()==exists.get().getChallengerId()) {
            winnerId = exists.get().getChallengedId();
        }
        else {
            winnerId = exists.get().getChallengerId();
        }

        gameSessionService.delete(exists.get());

        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.append("<@" + event.getMessageAuthor().getId() + ">! You rolled a 1! All your bases are belong to <@"+ winnerId + ">");

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setImage("https://media1.tenor.com/images/7066494e5810e5a84d68d5696004eec4/tenor.gif?itemid=7465431");
        messageBuilder.setEmbed(embedBuilder);
        messageBuilder.send(event.getChannel());
    }

    private void sendRegularMessage(MessageCreateEvent event, String result, String upperLimit) {
        event.getChannel().sendMessage("<@" + event.getMessageAuthor().getId() + "> rolled a " + result + " out of " + upperLimit);
    }
}
