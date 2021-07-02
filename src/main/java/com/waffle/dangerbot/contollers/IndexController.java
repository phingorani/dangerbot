package com.waffle.dangerbot.contollers;

import com.waffle.dangerbot.pojos.UserBasePojo;
import com.waffle.dangerbot.service.DiscordUserService;
import com.waffle.dangerbot.utilService.CsvDownloadUtilService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
        return "Welcome to Danger Bot! -Pratik Hingorani";
    }

    @GetMapping("version")
    String getVersion() {
        return appName + "\n Version: " + appVersion;
    }


    @GetMapping("downloadUserList/{discordId}")
    ResponseEntity<InputStreamResource> downloadUserList(@PathVariable String discordId) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = System.getenv("DISCORD_API_URL");

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bot " + System.getenv("DISCORD_TOKEN"));
        headers.set("Accept", "application/json");
        HttpEntity entity = new HttpEntity(headers);

        String fooResourceUrl
                = baseUrl + "/guilds/" + discordId + "/members";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(fooResourceUrl);
        builder.queryParam("limit", 1000);

        Date d1 = new Date();

        System.out.println("Hitting URL "+builder.toUriString());

        ResponseEntity<List<UserBasePojo>> response
                = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<UserBasePojo>>() {
        });
        Date d2 = new Date();
        Long seconds = (d2.getTime()-d1.getTime());
        System.out.println("Response at "+seconds.toString());

        List<String[]> dataLines = new ArrayList<>();
        response.getBody().forEach(userBasePojo -> {
            if (StringUtils.isEmpty(userBasePojo.nick)) {
                dataLines.add(new String[]{userBasePojo.user.id.toString(), userBasePojo.user.username});
            } else {
                dataLines.add(new String[]{userBasePojo.user.id.toString(), userBasePojo.nick});
            }
            System.out.println(userBasePojo.user.username);
        });

        File fileToDownload = CsvDownloadUtilService.dataArrayToCSV(dataLines);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(fileToDownload));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileToDownload.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }
}
