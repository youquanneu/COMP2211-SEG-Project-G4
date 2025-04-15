package com.campus.Repository.Mail;

import com.campus.Entity.Event.Event;
import com.campus.Entity.Mail.EventNotification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventNotificationRepository extends JpaRepository<EventNotification,Integer> {
    @Transactional
    List<EventNotification> findEventNotificationByEvent(Event event);
    @Query("select eventNotification from EventNotification eventNotification "+
            "where  (eventNotification.notificationTime <= :currentTime)  "+
            "and    (eventNotification.status           = 1)"
    )   // 1 in status is referred to pending
    List<EventNotification> findEventNotificationsNotSend(@Param("currentTime") LocalDateTime currentTime);
}
