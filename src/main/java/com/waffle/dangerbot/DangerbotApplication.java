package com.waffle.dangerbot;

import com.waffle.dangerbot.listners.RollListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DangerbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(DangerbotApplication.class, args);

        // Insert your bot's token here
        String token = process.env.DISCORD_TOKEN;

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        //Print server Name
        api.getServers().forEach(server -> {
            System.out.println("Server Name: "+server.getName());
        });

        // Add a listener \
        api.addListener(new RollListener());
    }

}
