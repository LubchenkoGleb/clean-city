package com.kpi.diploma.smartroads.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourcesServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
                .authorizeRequests()
                .antMatchers(
                        "/is-alive/welcome-page",
                        "/connect/**",
                        "/registration/**",
                        "/is-alive-rest/test",
                        "/sign-up/**",
                        "/sign-up-v3/**",
                        "/connect/**"
//                        ,
//                        "/connect2/**"
                )
                .permitAll()
                .anyRequest().authenticated()
//                .and()
//                .apply(new SpringSocialConfigurer())
        ;
    }


}
