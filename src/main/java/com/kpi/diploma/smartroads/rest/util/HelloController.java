package com.kpi.diploma.smartroads.rest.util;

import com.kpi.diploma.smartroads.model.security.MongoUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    @GetMapping(value = "/say")
    private ResponseEntity<String> sayHelloToPrincipal(/*Principal principal*/) {
        log.info("say hello to principal invoked");
//        MongoUserDetails userDetails = (MongoUserDetails) principal;
//        log.info("userDetails'{}'", userDetails.toString());
        return ResponseEntity.ok("lala");
    }
}
