//package com.kpi.diploma.smartroads.config.social;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.social.UserIdSource;
//import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
//import org.springframework.social.config.annotation.EnableSocial;
//import org.springframework.social.config.annotation.SocialConfigurer;
//import org.springframework.social.connect.ConnectionFactoryLocator;
//import org.springframework.social.connect.UsersConnectionRepository;
//import org.springframework.social.facebook.connect.FacebookConnectionFactory;
//
//@Slf4j
//@Configuration
//@EnableSocial
//public class SocialConfig implements SocialConfigurer {
//
////    private final Environment environment;
////
////    @Autowired
////    public SocialConfig(Environment environment) {
////        this.environment = environment;
////    }
//
//    @Override
//    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
//
//        FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(
//                environment.getProperty("social.facebook.appId"),
//                environment.getProperty("social.facebook.appSecret"));
//        facebookConnectionFactory.setScope("public_profile,email");
//
//        connectionFactoryConfigurer.addConnectionFactory(facebookConnectionFactory);
//    }
//
//    @Override
//    public UserIdSource getUserIdSource() {
//        return null;
//    }
//
//    @Override
//    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//        return null;
//    }
//
////    @Bean
////    public FacebookConnectionFactory getFacebookConnectionFactory() {
////
////        FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(
////                environment.getProperty("social.facebook.appId"),
////                environment.getProperty("social.facebook.appSecret"));
////
////        facebookConnectionFactory.setScope("public_profile,email");
////
////        return facebookConnectionFactory;
////    }
//
////    @Bean
////    @Scope(value="request", proxyMode = ScopedProxyMode.INTERFACES)
////    public Facebook facebook(ConnectionRepository repository) {
////        Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
////        return connection != null ? connection.getApi() : null;
////    }
//}
