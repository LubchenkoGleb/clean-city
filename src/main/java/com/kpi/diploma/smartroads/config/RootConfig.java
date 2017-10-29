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

@Slf4j
@Configuration
@EnableMongoAuditing
public class RootConfig {

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

//    @Bean
//    public Session connectToSmtp() {
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", environment.getProperty("email.host"));
//        props.put("mail.smtp.port", environment.getProperty("email.port"));
//        log.info("email props'{}'", props);
//
//        return Session.getInstance(props, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(environment.getProperty("email.username"),
//                        environment.getProperty("email.password"));
//            }
//        });
//    }
}
