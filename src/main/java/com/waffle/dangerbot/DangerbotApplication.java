package com.waffle.dangerbot;

import com.waffle.dangerbot.listeners.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class DangerbotApplication {

    @Autowired
    private RollListener rollListener;

    @Autowired
    private BotCommandListener botCommandListener;

    @Autowired
    private WelcomeListener welcomeListener;

    @Autowired
    private UpdateUsersListener updateUsersListener;

    @Autowired
    private AddRoleListener addRoleListener;

    private static final Logger logger = LogManager.getLogger(DangerbotApplication.class);


    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(DangerbotApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startApp() {
        // Insert your bot's token here
        String token = System.getenv("DISCORD_TOKEN");

        DiscordApi api = new DiscordApiBuilder()
                .setToken(token)
                .setAllIntents()
                .addListener(rollListener)
                .addListener(botCommandListener)
                .addListener(welcomeListener)
                .addListener(updateUsersListener)
                .addListener(addRoleListener)
                .login()
                .join();

        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
    }

}
