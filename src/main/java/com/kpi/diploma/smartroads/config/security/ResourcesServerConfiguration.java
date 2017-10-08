package com.kpi.diploma.smartroads.config.security;

import com.kpi.diploma.smartroads.config.social.SocialConnectionSignup;
import com.kpi.diploma.smartroads.config.social.SocialSignInAdapter;
import com.kpi.diploma.smartroads.service.security.MongoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableResourceServer
public class ResourcesServerConfiguration extends ResourceServerConfigurerAdapter {

    private final ConnectionFactoryLocator connectionFactoryLocator;
    private final UsersConnectionRepository usersConnectionRepository;
    private final SocialConnectionSignup socialConnectionSignup;
    private final MongoUserDetailsService userDetailsService;
    private final AuthorizationServerEndpointsConfiguration configuration;

    @Autowired
    public ResourcesServerConfiguration(ConnectionFactoryLocator connectionFactoryLocator,
                                        UsersConnectionRepository usersConnectionRepository,
                                        SocialConnectionSignup socialConnectionSignup,
                                        MongoUserDetailsService userDetailsService,
                                        AuthorizationServerEndpointsConfiguration configuration) {
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.usersConnectionRepository = usersConnectionRepository;
        this.socialConnectionSignup = socialConnectionSignup;
        this.userDetailsService = userDetailsService;
        this.configuration = configuration;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("resourceIdTest");
        super.configure(resources);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/registration/**",
                        "/connect/**",
                        "/signin/facebook",
                        "/after-redirect-controller/social-get-token/**",
                        "/is-alive/welcome-page",
                        "/is-alive-rest/test",
                        "/driver-requests/confirm",
                        "/manager-requests/confirm")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .apply(getSpringSocialConfigurer());
    }

    private SpringSocialConfigurer getSpringSocialConfigurer() {
        SpringSocialConfigurer config = new SpringSocialConfigurer();
        config.alwaysUsePostLoginUrl(true);
        config.postLoginUrl("/home");

        return config;
    }

    @Bean
    public ProviderSignInController providerSignInController() {
        usersConnectionRepository
                .setConnectionSignUp(socialConnectionSignup);

        ProviderSignInController providerSignInController = new ProviderSignInController(
                connectionFactoryLocator,
                usersConnectionRepository,
                new SocialSignInAdapter(configuration, userDetailsService));

        providerSignInController.setPostSignInUrl("/after-redirect-controller/social-get-token");

        return providerSignInController;
    }
}
