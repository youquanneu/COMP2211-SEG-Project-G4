package com.campus.Entity.Mail;

import com.campus.Classification.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;
    private String recipientEmail;
    private LocalDateTime notificationTime;
    private Status status;
}