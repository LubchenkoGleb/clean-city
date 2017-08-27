package com.kpi.diploma.smartroads.service;

import com.kpi.diploma.smartroads.model.document.User;
import com.kpi.diploma.smartroads.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements  UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existByEmail(String email) {
        log.info("'existByEmail' invoked with params'{}'", email);
        User userByEmail = userRepository.findByEmail(email);
        log.info("returned'{}'", userByEmail);
        return userByEmail == null;
    }
}
