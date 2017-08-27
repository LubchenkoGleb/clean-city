package com.kpi.diploma.smartroads.rest.primary;

import com.kpi.diploma.smartroads.model.document.User;
import com.kpi.diploma.smartroads.repository.UserRepository;
import com.kpi.diploma.smartroads.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
