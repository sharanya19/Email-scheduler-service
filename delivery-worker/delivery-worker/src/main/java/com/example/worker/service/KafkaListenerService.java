package com.example.worker.service;

import com.example.worker.model.Email;
import com.example.worker.repository.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  // Correct import for SLF4J
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class KafkaListenerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerService.class);
    private final EmailRepository emailRepository;
    private final KafkaTemplate<String, Email> kafkaTemplate;
    private final String topic = "email-scheduled-topic";

    public KafkaListenerService(EmailRepository emailRepository, KafkaTemplate<String, Email> kafkaTemplate) {
        this.emailRepository = emailRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    // Runs every minute
    @Scheduled(fixedRate = 60000)
    public void processPendingEmails() {
        List<Email> pendingEmails = emailRepository.findByStatusAndDeliveryTimestampLessThanEqual(
                Email.EmailStatus.PENDING, LocalDateTime.now());

        for (Email email : pendingEmails) {
            email.setStatus(Email.EmailStatus.IN_PROGRESS);
            emailRepository.save(email);

            logger.info("Sending email to Kafka: {}", email);
            kafkaTemplate.send(topic, email);
        }
    }
}
