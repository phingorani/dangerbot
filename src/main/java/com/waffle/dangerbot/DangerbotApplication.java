package com.waffle.dangerbot;

import com.waffle.dangerbot.listeners.BotCommandListener;
import com.waffle.dangerbot.listeners.RollListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class DangerbotApplication {

    private static final Logger logger = LoggerFactory.getLogger(DangerbotApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(DangerbotApplication.class, args);

        // Insert your bot's token here
        String token = System.getenv("DISCORD_TOKEN");

        System.out.println(token);

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        //Print server Name
        api.getServers().forEach(server -> {
            System.out.println("Server Name: "+server.getName());
        });

        // Add a listener \
        api.addListener(new RollListener());
        api.addListener(new BotCommandListener());
    }

}
