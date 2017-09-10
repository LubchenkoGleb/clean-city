package com.kpi.diploma.smartroads.config.social.v3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

@Configuration
@EnableSocial
@PropertySource("classpath:/application-secure.properties")
public class SocialConfigV3 implements SocialConfigurer {

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
                                       Environment environment) {
        FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(
                environment.getProperty("social.facebook.appId"),
                environment.getProperty("social.facebook.appSecret"));
        facebookConnectionFactory.setScope("email,public_profile");

        connectionFactoryConfigurer.addConnectionFactory(facebookConnectionFactory);
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new UserIdSource() {
            @Override
            public String getUserId() {
                return "test";
            }
        };
//        return new SessionUserIdSource();
    }

    @Bean
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        InMemoryUsersConnectionRepository inMemoryUsersConnectionRepository =
                new InMemoryUsersConnectionRepository(connectionFactoryLocator);

        return inMemoryUsersConnectionRepository;
    }
}
