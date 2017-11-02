//package com.kpi.diploma.smartroads.config.social;
//
//import com.kpi.diploma.smartroads.service.util.security.TokenService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.social.connect.Connection;
//import org.springframework.social.connect.web.SignInAdapter;
//import org.springframework.web.context.request.NativeWebRequest;
//
//@Slf4j
//public class SocialSignInAdapter implements SignInAdapter {
//
//    private TokenService tokenService;
//
//    public SocialSignInAdapter(TokenService tokenService) {
//        this.tokenService = tokenService;
//    }
//
//    @Override
//    public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
//        log.info("'signIn' invoked with params'{}'", localUserId);
//
//        String authority = tokenService.getToken(localUserId);
//        log.info("'authority={}'", authority);
//
//        return "/after-redirect-controller/social-get-token/" + authority;
//    }
//}