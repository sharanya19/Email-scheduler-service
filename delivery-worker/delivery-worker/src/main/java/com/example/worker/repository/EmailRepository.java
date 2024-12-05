package com.example.worker.repository;


import com.example.worker.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailRepository extends JpaRepository<Email, Long> {
    List<Email> findByStatusAndDeliveryTimestampLessThanEqual(Email.EmailStatus status, LocalDateTime currentTime);
}
