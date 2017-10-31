package com.kpi.diploma.smartroads.service.util.security;

import com.kpi.diploma.smartroads.model.document.user.User;
import com.kpi.diploma.smartroads.model.util.security.MongoUserDetails;
import com.kpi.diploma.smartroads.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MongoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MongoUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("loadUserByUsername invoked with params '{}'", s);

        User user = userRepository.findByEmail(s);
        log.info("returned user '{}'", user);

        return new MongoUserDetails(user);
    }
}
