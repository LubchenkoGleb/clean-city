package com.kpi.diploma.smartroads.service.util.email;

import com.kpi.diploma.smartroads.model.util.EmailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class EmailService {

    private final Session session;
    private final Environment environment;

    @Autowired
    public EmailService(Session session, Environment environment) {
        this.session = session;
        this.environment = environment;
    }

    public boolean send(EmailMessage email) throws MessagingException {
        log.info("'sendEmail' invoked with param: '{}'", email);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(environment.getProperty("email.username")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
        message.setSubject(email.getSubject());
        message.setContent(email.getMessage(), "text/html; charset=utf-8");
        Transport.send(message);

        return true;
    }
}
