package com.kpi.diploma.smartroads.rest.util.social;

import lombok.extern.slf4j.Slf4j;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/connect")
public class CustomSocialController extends ConnectController {

    public CustomSocialController(ConnectionFactoryLocator connectionFactoryLocator,
                                  ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator, connectionRepository);
    }

    @Override
    protected String connectedView(String providerId) {
        return "redirect:/after-redirect-controller/social-connected";
    }
}
