package com.kpi.diploma.smartroads.rest.primary;

import com.kpi.diploma.smartroads.model.document.user.User;
import com.kpi.diploma.smartroads.service.main.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/email")
    private ResponseEntity<User> registerByEmail(@RequestBody @Valid User user) {
        log.info("'registerByEmail' invoked with params'{}'", user);
        boolean result = userService.existByEmail(user.getEmail());
        return ResponseEntity.ok(user);
    }
}
