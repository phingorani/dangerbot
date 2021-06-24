package com.waffle.dangerbot.contollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
}
