package com.waffle.dangerbot;

import com.waffle.dangerbot.listners.RollListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class DangerbotApplication {

    public static void main(String[] args) {

        SpringApplication.run(DangerbotApplication.class, args);

        // Insert your bot's token here
//        String token = System.getenv("DISCORD_TOKEN");
        String token = "NDYzMzk5NTgxNzEyMTIxODU4.WzpkfQ.D11Gh5efHBboeRVwOjmUrclAh3U";

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        //Print server Name
        api.getServers().forEach(server -> {
            System.out.println("Server Name: "+server.getName());
        });

        // Add a listener \
        api.addListener(new RollListener());
    }

}
