package com.campus.Repository.Mail;

import com.campus.Entity.Mail.EventNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventNotificationRepository extends JpaRepository<EventNotification,Integer> {
}
