package com.kpi.diploma.smartroads.rest.util.social;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/after-redirect-controller")
public class AfterRedirectController {

    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    public AfterRedirectController(UsersConnectionRepository usersConnectionRepository) {
        this.usersConnectionRepository = usersConnectionRepository;
    }

    @GetMapping(value = "/social-connected")
    private ResponseEntity<User> socialConnected() {
        log.info("'socialConnected' invoked");

        ConnectionRepository loh = usersConnectionRepository.createConnectionRepository("test");
        Connection<Facebook> primaryConnection = loh.findPrimaryConnection(Facebook.class);
        String [] fields = { "email", "first_name", "last_name"};
        org.springframework.social.facebook.api.User userProfile = primaryConnection
                .getApi().fetchObject("me", org.springframework.social.facebook.api.User.class, fields);

        return ResponseEntity.ok(userProfile);
    }

    @GetMapping(value = "/social-get-token/{token}")
    private ResponseEntity<String> socialGetToken(@PathVariable String token) {
        log.info("'socialGetToken' invoked with params'{}'", token);
        return ResponseEntity.ok(token);
    }


}

