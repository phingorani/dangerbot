package com.waffle.dangerbot.contollers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @Value("${info.build.version}")
    String appVersion;

    @Value("${info.build.name}")
    String appName;

    @GetMapping()
    String home() {
        return "Welcome to Danger Bot! -Pratik Hingorani" ;
    }

    @GetMapping("version")
    String getVersion() {
        return appName +"\n Version: "+ appVersion;
    }
}
