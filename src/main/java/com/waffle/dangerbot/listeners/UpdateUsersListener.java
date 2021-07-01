package com.waffle.dangerbot.listeners;

import com.waffle.dangerbot.entity.DiscordUser;
import com.waffle.dangerbot.pojos.UserBasePojo;
import com.waffle.dangerbot.repository.DiscordUserRepository;
import com.waffle.dangerbot.utilService.BotUtilService;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class UpdateUsersListener implements MessageCreateListener {

    @Autowired
    DiscordUserRepository discordUserRepository;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        if (BotUtilService.isValidateBotCommand(event)) {
            if (BotUtilService.isAdmin(event) && BotUtilService.isUpdateUsers(event)) {
                getUsersFromDiscord(event);
            }
        }
    }

    private void getUsersFromDiscord(MessageCreateEvent event) {

        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = System.getenv("DISCORD_API_URL");

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bot "+event.getApi().getToken());
        headers.set("Accept", "application/json");
        HttpEntity entity = new HttpEntity(headers);

        String fooResourceUrl
                = baseUrl+"/guilds/"+event.getServer().get().getId()+"/members";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(fooResourceUrl);
        builder.queryParam("limit", 1000);

        ResponseEntity<List<UserBasePojo>> response
                = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<UserBasePojo>>() {
        });

       response.getBody().stream().forEach(userBasePojo -> {
           if (discordUserRepository.existsById(userBasePojo.user.id)) {
               DiscordUser discordUser = new DiscordUser();
               if(StringUtils.isEmpty(userBasePojo.nick)) {
                   discordUser.setDisplayName(userBasePojo.user.username);
               }
               else {
                   discordUser.setDisplayName(userBasePojo.nick);
               }
               discordUser.setDiscordId(userBasePojo.user.id);
               System.out.println(discordUser.getDiscordId() + " + " + discordUser.getDisplayName());
               discordUserRepository.save(discordUser);
           }
       });
    }
}
