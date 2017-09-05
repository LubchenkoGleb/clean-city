//package com.kpi.diploma.smartroads.config.social.v1;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.social.facebook.connect.FacebookConnectionFactory;
//import org.springframework.social.oauth2.OAuth2Operations;
//
//@Configuration
//public class SocialConfigV1 {
//
//    private final Environment environment;
//
//    @Autowired
//    public SocialConfigV1(Environment environment) {
//        this.environment = environment;
//    }
//
//    @Bean
//    public FacebookConnectionFactory getFacebookConnectionFactory() {
//
//        FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(
//                environment.getProperty("social.facebook.appId"),
//                environment.getProperty("social.facebook.appSecret"));
//
//        facebookConnectionFactory.setScope("email,public_profile");
//
//        return facebookConnectionFactory;
//    }
//
//    @Bean
//    public OAuth2Operations getFacebookOauth2Operations() {
//        return getFacebookConnectionFactory().getOAuthOperations();
//    }
//}
