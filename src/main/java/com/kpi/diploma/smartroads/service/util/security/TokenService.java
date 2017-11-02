package com.kpi.diploma.smartroads.service.util.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Slf4j
@Service
public class TokenService {

    private final UserDetailsService userDetailsService;
    private final AuthorizationServerEndpointsConfiguration configuration;

    @Autowired
    public TokenService(AuthorizationServerEndpointsConfiguration configuration,
                        UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.configuration = configuration;
    }


    public String getToken(String userName) {
        log.info("'getToken' invoked with params'{}'", userName);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        Map<String, String> requestParameters = new HashMap<>();
        Map<String, Serializable> extensionProperties = new HashMap<>();

        boolean approved = true;
        Set<String> responseTypes = new HashSet<>();
        responseTypes.add("code");

        OAuth2Request oauth2Request = new OAuth2Request(
                requestParameters,
                "my-trusted-client",
                userDetails.getAuthorities(),
                approved,
                new HashSet<>(),
                new HashSet<>(Arrays.asList("resourceIdTest")),
                null,
                responseTypes,
                extensionProperties);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), "N/A", userDetails.getAuthorities());

        OAuth2Authentication auth = new OAuth2Authentication(oauth2Request, authenticationToken);
        AuthorizationServerTokenServices tokenService = configuration.getEndpointsConfigurer().getTokenServices();
        OAuth2AccessToken token = tokenService.createAccessToken(auth);

        return token.getValue();
    }
}
