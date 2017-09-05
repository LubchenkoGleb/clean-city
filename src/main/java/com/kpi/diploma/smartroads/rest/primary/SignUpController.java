//package com.kpi.diploma.smartroads.rest.primary;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.social.connect.Connection;
//import org.springframework.social.connect.ConnectionRepository;
//import org.springframework.social.facebook.api.Facebook;
//import org.springframework.social.facebook.api.User;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Slf4j
//@Controller
//@RequestMapping("/sign-up")
//public class SignUpController {
//
//    private Facebook facebook;
//    private ConnectionRepository connectionRepository;
//    private ObjectMapper mapper;
//
//    public SignUpController(
//            Facebook facebook,
//                            ConnectionRepository connectionRepository
//    ) {
//        this.facebook = facebook;
//        this.connectionRepository = connectionRepository;
//        this.mapper = new ObjectMapper();
//    }
//
//    @GetMapping(value = "/facebook")
//    public String helloFacebook() {
//        log.info("'helloFacebook' invoked");
//
//        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
//            log.info("return user to redirect");
//            return "redirect:/sign-up-facebook/1.html";
//        }
//
////        Connection<Facebook> primaryConnection = connectionRepository.getPrimaryConnection(Facebook.class);
////        primaryConnection.getApi()
//
//
//        String [] fields = { "email", "first_name"};
//        log.info("fields");
//        User userProfile = facebook.fetchObject("me", User.class, fields);
//
//        log.info("fetched user'{}'", userProfile);
//        log.info("fetched user.email'{}'", userProfile.getEmail());
//        log.info("fetched user.first_name'{}'", userProfile.getFirstName());
//        log.info("fetched user.id'{}'", userProfile.getId());
//
////        ObjectNode response = mapper.createObjectNode();
////        response.put("facebookEmail", facebook.userOperations().getUserProfile().getEmail());
////        response.put("firstName", facebook.userOperations().getUserProfile().getFirstName());
////        response.put("lastName", facebook.userOperations().getUserProfile().getLastName());
//        return "welcome-page";
//    }
//
//}
