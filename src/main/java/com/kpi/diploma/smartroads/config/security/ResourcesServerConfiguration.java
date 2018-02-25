package com.kpi.diploma.smartroads.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

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
                .antMatchers("/map-object-requests/create",
                        "/map-object-requests/set-company").hasRole("ADMIN")
                .antMatchers(
                        "/company-requests/create-driver",
                        "/company-requests/create-manager").hasRole("COMPANY")
                .antMatchers(
                        "/",
                        "/registration/**",
                        "/is-alive/welcome-page",
                        "/is-alive-rest/test",
                        "/driver-requests/confirm",
                        "/manager-requests/confirm",
                        "/socket/map-object/all/**").permitAll()
                .anyRequest().authenticated();
    }
}
