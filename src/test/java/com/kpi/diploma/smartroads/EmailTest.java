package com.kpi.diploma.smartroads;

import com.kpi.diploma.smartroads.model.util.EmailMessage;
import com.kpi.diploma.smartroads.service.util.email.EmailService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailTest {

    @Autowired
    private EmailService emailService;

    @Test
    @Ignore
    public void test_emailSend() {
        EmailMessage testMessage = new EmailMessage(
                "gleb.lubchenko@gmail.com",
                "test",
                "<!DOCTYPE html> " +
                "<html> " +
                "<head> " +
                    "<style> " +
                        "body {background-color: blue;} " +
                        "h1   {color: blue;} " +
                        "p    {color: red;} " +
                    "</style> " +
                "</head> " +
                "<body> " +
                    "<h1>This is a heading</h1> " +
                    "<p>This is a paragraph.</p> " +
                "</body> " +
                "</html>");
        boolean isSend = emailService.send(testMessage);
        Assert.assertTrue(isSend);
    }

//    @Test
//    public void test_regex() {
//        String url =  "https://res.cloudinary.com/demo/image/upload/v1375302801/tquyfignx5bxcbsupr6a.jpg";
//        Matcher matcher = Pattern.compile("[^/]*(?=[.][a-zA-Z]+$)").matcher(url);
//        if(matcher.find()) System.out.println("+++");
//        String id = Pattern.compile("[^/]*(?=[.][a-zA-Z]+$)").matcher(url).group(1);
//        System.out.println(id);
//    }
}
