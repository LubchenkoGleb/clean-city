package com.kpi.diploma.smartroads.rest.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/is-alive-rest")
public class TestRestController {

    @RequestMapping(value = "/test")
    private ResponseEntity<String> welcomePage() {
        log.info("welcome-page was visited");
        return ResponseEntity.ok("rest test is alive");
    }

}

