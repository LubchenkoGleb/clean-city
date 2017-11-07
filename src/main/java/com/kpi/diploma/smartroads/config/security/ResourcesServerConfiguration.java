package com.kpi.diploma.smartroads.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableResourceServer
public class ResourcesServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("resourceIdTest");
        super.configure(resources);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .cors().and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(
                        "/company-requests/create-driver",
                        "/company-requests/create-manager")
                .hasRole("COMPANY")
                .antMatchers(
                        "/",
                        "/registration/**",
                        "/connect/**",
                        "/signin/facebook",
                        "/after-redirect-controller/social-get-token/**",
                        "/is-alive/welcome-page",
                        "/is-alive-rest/test",
                        "/driver-requests/confirm",
                        "/manager-requests/confirm",
                        "/echo-all.html",
                        "/echo-all/**",
                        "/echo-my.html"
//                        ,
//                        "/echo-my/info"
                        )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and();
    }



//    @Bean
//    public ProviderSignInController providerSignInController() {
//        usersConnectionRepository
//                .setConnectionSignUp(socialConnectionSignup);
//
//        ProviderSignInController providerSignInController = new ProviderSignInController(
//                connectionFactoryLocator,
//                usersConnectionRepository,
//                new SocialSignInAdapter(tokenService));
//
////        providerSignInController.setPostSignInUrl("/after-redirect-controller/social-get-token");
//
//        return providerSignInController;
//    }
}
