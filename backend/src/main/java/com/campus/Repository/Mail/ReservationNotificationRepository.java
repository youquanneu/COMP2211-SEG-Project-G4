package com.campus.Repository.Mail;


import com.campus.Entity.Mail.ReservationNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationNotificationRepository extends JpaRepository<ReservationNotification,Integer> {
}
