package com.kpi.diploma.smartroads.rest.primary;

import com.kpi.diploma.smartroads.model.document.User;
import com.kpi.diploma.smartroads.repository.UserRepository;
import com.kpi.diploma.smartroads.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {

    private final UserService userService;
    private final UsersConnectionRepository usersConnectionRepository;

    @Autowired
    public RegistrationController(UserService userService, UsersConnectionRepository usersConnectionRepository) {
        this.userService = userService;
        this.usersConnectionRepository = usersConnectionRepository;
    }

    @PostMapping(value = "/email")
    private ResponseEntity<User> registerByEmail(@RequestBody @Valid User user) {
        log.info("'registerByEmail' invoked with params'{}'", user);
        boolean result = userService.existByEmail(user.getEmail());
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/social-connected")
    private ResponseEntity<org.springframework.social.facebook.api.User> socialConnected() {
        log.info("'socialConnected' invoked");

        ConnectionRepository loh = usersConnectionRepository.createConnectionRepository("test");
        Connection<Facebook> primaryConnection = loh.findPrimaryConnection(Facebook.class);
        String [] fields = { "email", "first_name", "last_name"};
        org.springframework.social.facebook.api.User userProfile = primaryConnection
                .getApi().fetchObject("me", org.springframework.social.facebook.api.User.class, fields);

        return ResponseEntity.ok(userProfile);
    }
}
