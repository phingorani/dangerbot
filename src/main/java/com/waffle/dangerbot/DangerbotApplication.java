package com.waffle.dangerbot;

import com.waffle.dangerbot.listeners.BotCommandListener;
import com.waffle.dangerbot.listeners.RollListener;
import com.waffle.dangerbot.listeners.UpdateUsersListener;
import com.waffle.dangerbot.listeners.WelcomeListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class DangerbotApplication {

    @Autowired
    private RollListener rollListener;

    @Autowired
    private BotCommandListener botCommandListener;

    @Autowired
    private WelcomeListener welcomeListener;

    @Autowired
    private UpdateUsersListener updateUsersListener;

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(DangerbotApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startApp() {
        // Insert your bot's token here
        String token = System.getenv("DISCORD_TOKEN");

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        //Print server Name
        api.getServers().forEach(server -> {
            System.out.println("Server Name: "+server.getName());
        });

        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());

        // Add a listener
        api.addListener(rollListener);
        api.addListener(botCommandListener);
        api.addListener(welcomeListener);
        api.addListener(updateUsersListener);
    }

}
