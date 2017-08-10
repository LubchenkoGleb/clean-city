package com.kpi.diploma.smartroads.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class StaticController {

    @RequestMapping("/welcome-page")
    @ResponseBody
    private String welcomePage() {
        log.info("welcome-page was visited");
        return "welcome-page";
    }
}
