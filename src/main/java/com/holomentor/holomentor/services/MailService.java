package com.holomentor.holomentor.services;

import com.holomentor.holomentor.models.SendGridMail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private Environment env;

    @Autowired
    private SendGridMail sendGridMail;

    public String sendMail(SendGridMail.TemplateNames template, String to, String subject, HashMap<String, String> data) throws IOException {

        Email senderEmail = new Email(env.getProperty("env.holomentor.email"));
        Email receiverEmail = new Email(to);

        SendGrid sg = new SendGrid(env.getProperty("env.sendgrid.api_key"));

        Mail mail = new Mail();
        mail.setFrom(senderEmail);
        mail.setSubject(subject);
        mail.setTemplateId(sendGridMail.getTemplateID(template));

        Personalization personalization = new Personalization();
        personalization.addTo(receiverEmail);

//        set dynamic data to the template
        if(!data.isEmpty()) {
            for (Map.Entry<String, String> set: data.entrySet()) {
                personalization.addDynamicTemplateData(set.getKey(), set.getValue());
            }
        }
        mail.addPersonalization(personalization);

        Request request = new Request();

        // try sending the mail
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);
            logger.info(response.getBody());
            return response.getBody();
        } catch (IOException ex){
            throw ex;
        }
    }

}
