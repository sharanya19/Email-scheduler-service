package com.example.scheduler.controller;

import com.example.scheduler.model.Email;
import com.example.scheduler.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emails")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/schedule")
    @ResponseStatus(HttpStatus.CREATED)
    public Email scheduleEmail(@RequestBody Email email) {
        return emailService.scheduleEmail(email);
    }

    @GetMapping
    public List<Email> getAllScheduledEmails() {
        return emailService.getAllScheduledEmails();
    }

    @GetMapping("/{id}")
    public Optional<Email> getEmailById(@PathVariable Long id) {
        return emailService.getEmailById(id);
    }

    @PutMapping("/{id}")
    public void updateEmail(@PathVariable Long id, @RequestBody Email email) {
        emailService.updateEmail(id, email);
    }

    @DeleteMapping("/{id}")
    public void cancelEmail(@PathVariable Long id) {
        emailService.cancelEmail(id);
    }

}
