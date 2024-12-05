package com.example.scheduler.service;

import com.example.scheduler.model.Email;
import com.example.scheduler.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    public Email scheduleEmail(Email email) {
        email.setStatus(Email.EmailStatus.PENDING);
        return emailRepository.save(email);
    }

    public List<Email> getAllScheduledEmails() {
        return emailRepository.findAll();
    }

    public Optional<Email> getEmailById(Long id) {
        return emailRepository.findById(id);
    }

    public void updateEmail(Long id, Email emailDetails) {
        Email email = emailRepository.findById(id).orElseThrow(() -> new RuntimeException("Email not found"));
        email.setSubject(emailDetails.getSubject());
        email.setBody(emailDetails.getBody());
        email.setHtmlBody(emailDetails.getHtmlBody());
        email.setRecipients(emailDetails.getRecipients());
        email.setDeliveryTimestamp(emailDetails.getDeliveryTimestamp());
        emailRepository.save(email);
    }

    public void cancelEmail(Long id) {
        Email email = emailRepository.findById(id).orElseThrow(() -> new RuntimeException("Email not found"));
        email.setStatus(Email.EmailStatus.FAILED); // You can mark it as canceled or failed
        emailRepository.save(email);
    }


}
