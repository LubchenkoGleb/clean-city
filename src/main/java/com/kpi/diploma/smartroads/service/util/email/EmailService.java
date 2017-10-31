package com.kpi.diploma.smartroads.service.util.email;

import com.kpi.diploma.smartroads.model.util.exception.EmailException;
import com.kpi.diploma.smartroads.model.util.data.EmailMessage;
import com.sendgrid.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class EmailService {

    private final SendGrid sendGrid;

    @Autowired
    public EmailService(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    public boolean send(EmailMessage email) {
        log.info("'sendEmail' invoked with param: '{}'", email);

        Email from = new Email("no-reply@cleancity-web.herokuapp.com");
        Email to = new Email(email.getTo());
        Content content = new Content("text/html", email.getMessage());
        Mail mail = new Mail(from, email.getSubject(), to, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw new EmailException("email wasn't sent");
        }
        return true;
    }
}
