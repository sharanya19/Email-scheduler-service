package com.example.scheduler.repository;

import com.example.scheduler.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailRepository extends JpaRepository<Email, Long> {
    List<Email> findByStatusAndDeliveryTimestampBefore(Email.EmailStatus status, LocalDateTime now);
}
