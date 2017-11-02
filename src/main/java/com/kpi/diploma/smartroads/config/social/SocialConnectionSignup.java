//package com.kpi.diploma.smartroads.config.social;
//
//import com.kpi.diploma.smartroads.model.document.user.User;
//import com.kpi.diploma.smartroads.repository.UserRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.springframework.social.connect.Connection;
//import org.springframework.social.connect.ConnectionSignUp;
//import org.springframework.social.connect.UserProfile;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//public class SocialConnectionSignup implements ConnectionSignUp {
//
//    private final UserRepository userRepository;
//
//    public SocialConnectionSignup(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public String execute(Connection<?> connection) {
//        log.info("'execute' invoked with params'{}'", connection);
//
//        UserProfile profile = connection.fetchUserProfile();
//        User user = new User(profile.getEmail(), RandomStringUtils.randomAlphabetic(10));
//        user = userRepository.save(user);
//        log.info("'user={}'", user);
//
//        return user.getEmail();
//    }
//}
