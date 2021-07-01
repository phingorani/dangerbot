package com.waffle.dangerbot.contollers;

import com.waffle.dangerbot.pojos.UserBasePojo;
import com.waffle.dangerbot.service.DiscordUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.Access;
import java.util.List;

@RestController
@RequestMapping("/")
public class IndexController {

    @Value("${info.build.version}")
    String appVersion;

    @Value("${info.build.name}")
    String appName;

    @Autowired
    DiscordUserService discordUserService;

    @GetMapping()
    String home() {
        return "Welcome to Danger Bot! -Pratik Hingorani" ;
    }

    @GetMapping("version")
    String getVersion() {
        return appName +"\n Version: "+ appVersion;
    }


    @GetMapping("updateUserList/{discordId}")
    void updateUserList(@PathVariable String discordId) {
        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = System.getenv("DISCORD_API_URL");

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bot "+System.getenv("DISCORD_TOKEN"));
        headers.set("Accept", "application/json");
        HttpEntity entity = new HttpEntity(headers);

        String fooResourceUrl
                = baseUrl+"/guilds/"+discordId+"/members";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(fooResourceUrl);
        builder.queryParam("limit", 1000);

        ResponseEntity<List<UserBasePojo>> response
                = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<UserBasePojo>>() {
        });

        discordUserService.saveUserList(response.getBody());
    }
}
