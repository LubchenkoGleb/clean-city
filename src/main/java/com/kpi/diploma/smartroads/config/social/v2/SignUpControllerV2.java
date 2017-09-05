//package com.kpi.diploma.smartroads.config.social.v2;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.social.connect.Connection;
//import org.springframework.social.connect.ConnectionFactory;
//import org.springframework.social.connect.ConnectionFactoryLocator;
//import org.springframework.social.facebook.api.Facebook;
//import org.springframework.social.facebook.api.User;
//import org.springframework.social.facebook.connect.FacebookConnectionFactory;
//import org.springframework.social.oauth2.AccessGrant;
//import org.springframework.social.oauth2.OAuth2Parameters;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
//@RestController
//@RequestMapping(value = "/sign-up-v1")
//public class SignUpControllerV2 {
//
//    private final FacebookConnectionFactory connectionFactory;
//
//    @Autowired
//    public SignUpControllerV2(ConnectionFactoryLocator connectionFactoryLocator) {
//        this.connectionFactory = (FacebookConnectionFactory) connectionFactoryLocator.getConnectionFactory(Facebook.class);
//    }
//
//    @GetMapping(value = "/facebook")
//    private void signUpFacebook(HttpServletResponse response) throws IOException {
//        log.info("signUpFaceBook");
//
//        OAuth2Parameters params = new OAuth2Parameters();
//        params.setRedirectUri("http://localhost:8000/sign-up-v1/redirect-uri");
//        params.setScope(connectionFactory.getScope());
//        String authorizeUrl = connectionFactory.getOAuthOperations().buildAuthorizeUrl(params);
//        response.sendRedirect(authorizeUrl);
//    }
//
//    @GetMapping(value = "/redirect-uri")
//    private void redirectUri(HttpServletResponse response, @RequestParam String code) {
//        log.info("'redirectUri' invoked with params'{}'", code);
//
//        AccessGrant accessGrant = connectionFactory
//                .getOAuthOperations()
//                .exchangeForAccess(code, "http://localhost:8000/sign-up-v1/redirect-uri", null);
//        Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
//        Facebook facebook = connection.getApi();
//
//        String [] fields = {"first_name", "last_name", "email"};
//        User userProfile = facebook.fetchObject("me", User.class, fields);
//
//        log.info("fetched user.id'{}'", userProfile.getId());
//        log.info("fetched user.email'{}'", userProfile.getEmail());
//        log.info("fetched user.last_name'{}'", userProfile.getLastName());
//        log.info("fetched user.first_name'{}'", userProfile.getFirstName());
//    }
//}
