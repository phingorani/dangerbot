package com.waffle.dangerbot;

import com.waffle.dangerbot.listeners.BotCommandListener;
import com.waffle.dangerbot.listeners.RollListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;


@SpringBootApplication
public class DangerbotApplication {

    @Autowired
    private RollListener rollListener;

    @Autowired
    private BotCommandListener botCommandListener;

    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(DangerbotApplication.class, args);


    }

    @EventListener(ApplicationReadyEvent.class)
    public void startApp() {
        // Insert your bot's token here
        String token = System.getenv("DISCORD_TOKEN");

        System.out.println(token);

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        //Print server Name
        api.getServers().forEach(server -> {
            System.out.println("Server Name: "+server.getName());
        });

        // Add a listener \
        api.addListener(rollListener);
        api.addListener(botCommandListener);
    }

}
