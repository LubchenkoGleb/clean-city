package com.kpi.diploma.smartroads.config.social;//package com.kpi.diploma.smartroads.config.social.v3;
//
//import com.kpi.diploma.smartroads.repository.UserRepository;
//import FacebookConnectionSignup;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.social.UserIdSource;
//import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
//import org.springframework.social.config.annotation.EnableSocial;
//import org.springframework.social.config.annotation.SocialConfigurer;
//import org.springframework.social.connect.ConnectionFactoryLocator;
//import org.springframework.social.connect.UsersConnectionRepository;
//import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
//import org.springframework.social.facebook.connect.FacebookConnectionFactory;
//import org.springframework.social.security.AuthenticationNameUserIdSource;
//
//@Configuration
//@EnableSocial
//@PropertySource("classpath:/application-secure.properties")
//public class SocialConfigV3 implements SocialConfigurer {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
//                                       Environment environment) {
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
//        return new AuthenticationNameUserIdSource();
//    }
//
//    @Override
//    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//        InMemoryUsersConnectionRepository inMemoryUsersConnectionRepository =
//                new InMemoryUsersConnectionRepository(connectionFactoryLocator);
//
//        inMemoryUsersConnectionRepository.setConnectionSignUp(new FacebookConnectionSignup(userRepository));
//        return inMemoryUsersConnectionRepository;
//    }
//}
