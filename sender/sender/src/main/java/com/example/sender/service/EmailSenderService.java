package com.example.sender.service;

import com.example.sender.model.Email;
import com.example.sender.repository.EmailRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;


public class EmailSenderService {
    private final EmailRepository emailRepository;
    private final KafkaTemplate<String, Email> kafkaTemplate;
    private final String topic = "email-scheduled-topic";
    private final String emailStatusTopic = "email-status-topic";  // Kafka topic for WebSocket Service
    private final AmazonSESClient amazonSESClient;

    public EmailSenderService(EmailRepository emailRepository, KafkaTemplate<String, Email> kafkaTemplate, AmazonSESClient amazonSESClient) {
        this.emailRepository = emailRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.amazonSESClient = amazonSESClient;
    }

    // Listen to the Kafka topic for new email events
    @KafkaListener(topics = topic, groupId = "email-sender-group")
    public void consumeEmailMessage(ConsumerRecord<String, Email> record) {
        Email email = record.value();
        try {
            boolean isSent = sendEmail(email);
            if (isSent) {
                email.setStatus(Email.EmailStatus.SENT);
                // Send status message to the status Kafka topic for WebSocket notifications
                kafkaTemplate.send(emailStatusTopic, email);
            } else {
                email.setStatus(Email.EmailStatus.FAILED);
                kafkaTemplate.send(emailStatusTopic, email);
            }
            emailRepository.save(email);
        } catch (Exception e) {
            email.setStatus(Email.EmailStatus.FAILED);
            emailRepository.save(email);
            kafkaTemplate.send(emailStatusTopic, email);
        }
    }

    private boolean sendEmail(Email email) {
        return amazonSESClient.sendEmail(email);  // Call Amazon SES to send the email
    }
}
