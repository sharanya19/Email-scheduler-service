package com.example.sender.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.example.sender.model.Email;
import org.springframework.stereotype.Service;

@Service
public class AmazonSESClient {

    private final AmazonSimpleEmailService sesClient;

    public AmazonSESClient() {
        this.sesClient = AmazonSimpleEmailServiceClientBuilder.standard().build();
    }

    public boolean sendEmail(Email email) {
        try {
            SendEmailRequest sendEmailRequest = new SendEmailRequest()
                    .withSource("your-sender-email@example.com")
                    .withDestination(new Destination().withToAddresses(email.getRecipients()))
                    .withMessage(new Message()
                            .withSubject(new Content().withData(email.getSubject()))
                            .withBody(new Body().withText(new Content().withData(email.getBody()))));

            sesClient.sendEmail(sendEmailRequest);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
