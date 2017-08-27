package com.kpi.diploma.smartroads.config.social;

import com.kpi.diploma.smartroads.service.security.MongoUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import java.io.Serializable;
import java.util.*;

@Slf4j
public class SocialSignInAdapter implements SignInAdapter {

    private AuthorizationServerEndpointsConfiguration configuration;
    private MongoUserDetailsService mongoUserDetailsService;

    public SocialSignInAdapter(AuthorizationServerEndpointsConfiguration configuration,
                               MongoUserDetailsService mongoUserDetailsService) {
        this.configuration = configuration;
        this.mongoUserDetailsService = mongoUserDetailsService;
    }

    @Override
    public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        connection.getDisplayName(), null,
                        Arrays.asList(new SimpleGrantedAuthority("FACEBOOK_USER"))));

        UserDetails userDetails = mongoUserDetailsService.loadUserByUsername(localUserId);
        String authority = createAuthority(userDetails);
        log.info("'authority={}'", authority);

        return "/after-redirect-controller/social-get-token/" + authority;
    }

    public String createAuthority(UserDetails userDetails) {

        Map<String, String> requestParameters = new HashMap<>();
        Map<String, Serializable> extensionProperties = new HashMap<>();

        boolean approved = true;
        Set<String> responseTypes = new HashSet<>();
        responseTypes.add("code");

        // Authorities
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