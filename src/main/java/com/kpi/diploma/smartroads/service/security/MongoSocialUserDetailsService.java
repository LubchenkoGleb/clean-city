//package com.kpi.diploma.smartroads.service.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.social.security.SocialUserDetails;
//import org.springframework.social.security.SocialUserDetailsService;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MongoSocialUserDetailsService implements SocialUserDetailsService {
//
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    public MongoSocialUserDetailsService(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
//        return (SocialUserDetails) userDetails;
//    }
//}
