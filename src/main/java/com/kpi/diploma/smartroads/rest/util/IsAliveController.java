package com.kpi.diploma.smartroads.rest.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping(value = "/is-alive")
public class IsAliveController {

    @RequestMapping("/welcome-page")
    @ResponseBody
    private String welcomePage() {
        log.info("welcome-page was visited");
        return "welcome-page";
    }

    @RequestMapping("/private-page")
    @ResponseBody
    private String privatePage() {
        log.info("private-page was visited");
        return "private-page";
    }

}
