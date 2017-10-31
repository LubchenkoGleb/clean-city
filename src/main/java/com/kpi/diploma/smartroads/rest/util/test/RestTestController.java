package com.kpi.diploma.smartroads.rest.util.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping(value = "/is-alive-rest")
public class RestTestController {

    @RequestMapping(value = "/test/unsecure")
    private ResponseEntity<String> welcomePageUnsecure() {
        log.info("welcome-page was visited");
        return ResponseEntity.ok("is alive - unsecure");
    }

    @RequestMapping(value = "/test/secure")
    private ResponseEntity<String> welcomePageSecure(Principal principal) {
        log.info("welcome-page was visited");
        return ResponseEntity.ok("is alive - secure. User - " + principal.getName());
    }

}

