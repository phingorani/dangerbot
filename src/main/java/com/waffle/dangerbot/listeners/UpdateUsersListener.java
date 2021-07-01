package com.waffle.dangerbot.listeners;

import com.waffle.dangerbot.entity.DiscordUser;
import com.waffle.dangerbot.repository.DiscordUserRepository;
import com.waffle.dangerbot.utilService.BotUtilService;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class UpdateUsersListener implements MessageCreateListener {

    @Autowired
    DiscordUserRepository discordUserRepository;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        if (BotUtilService.isValidateBotCommand(event)) {
            if (BotUtilService.isAdmin(event) && BotUtilService.isUpdateUsers(event)) {

                getUsersFromDiscord(event);

                event.getServer().get().getRoles().forEach(role -> System.out.println(role.getName()));
                event.getServer().get().getMembers().forEach(user -> {
                    if (discordUserRepository.existsById(user.getId())) {
                        DiscordUser discordUser = new DiscordUser();
                        discordUser.setDisplayName(user.getName());
                        discordUser.setDiscordId(user.getId());
                        discordUserRepository.save(discordUser);
                    }
                });
            }
        }
    }

    private void getUsersFromDiscord(MessageCreateEvent event) {

        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = System.getenv("DISCORD_API_URL");

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", event.getApi().getToken());
        headers.set("Accept", "application/json");
        HttpEntity entity = new HttpEntity(headers);

        String fooResourceUrl
                = baseUrl+"/guilds/"+event.getServer().get().getId()+"/members";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(fooResourceUrl);
        builder.queryParam("limit", 1000);


        ResponseEntity<?> response
                = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,User.class);

        System.out.println(response.getBody());
    }
}
