package com.kpi.diploma.smartroads.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kpi.diploma.smartroads.config.db.UserCascadeSaveEL;
import com.sendgrid.SendGrid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Slf4j
@Configuration
@EnableMongoAuditing
public class RootConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SimpleWebSocketHandler(), "/echo").withSockJS();
    }

    private final Environment environment;

    @Autowired
    public RootConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public UserCascadeSaveEL userCascadingMongoEventListener() {
        return new UserCascadeSaveEL();
    }

    @Bean
    public Cloudinary getCloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", environment.getProperty("cloudinary.name"),
                "api_key", environment.getProperty("cloudinary.key"),
                "api_secret", environment.getProperty("cloudinary.secret")));
        return cloudinary;
    }

    @Bean
    public SendGrid getSendGrid() {
        return new SendGrid(environment.getProperty("sendgrid.key"));
    }
}
