//package com.kpi.diploma.smartroads.config.social.v2;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.social.UserIdSource;
//import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
//import org.springframework.social.config.annotation.EnableSocial;
//import org.springframework.social.config.annotation.SocialConfigurer;
//import org.springframework.social.connect.ConnectionFactory;
//import org.springframework.social.connect.ConnectionFactoryLocator;
//import org.springframework.social.connect.ConnectionRepository;
//import org.springframework.social.connect.UsersConnectionRepository;
//import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
//import org.springframework.social.connect.web.ConnectController;
//import org.springframework.social.facebook.api.Facebook;
//import org.springframework.social.facebook.connect.FacebookConnectionFactory;
//import org.springframework.social.security.AuthenticationNameUserIdSource;
//
//@Configuration
//@EnableSocial
//public class SocialConfigV2 implements SocialConfigurer {
//
//    @Override
//    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
//
//        FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(
//                environment.getProperty("social.facebook.appId"),
//                environment.getProperty("social.facebook.appSecret"));
//        facebookConnectionFactory.setScope("email,public_profile");
//
//        connectionFactoryConfigurer.addConnectionFactory(facebookConnectionFactory);
//    }
//
//    @Override
//    public UserIdSource getUserIdSource() {
////        return new AuthenticationNameUserIdSource();
//        return null;
//    }
//
//    @Override
//    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
////        return new InMemoryUsersConnectionRepository(connectionFactoryLocator);
//        return null;
//    }
//
////    @Bean
////    public ConnectController connectController(
////            ConnectionFactoryLocator connectionFactoryLocator,
////            ConnectionRepository connectionRepository) {
////        return new ConnectController(connectionFactoryLocator, connectionRepository);
////    }
//}
