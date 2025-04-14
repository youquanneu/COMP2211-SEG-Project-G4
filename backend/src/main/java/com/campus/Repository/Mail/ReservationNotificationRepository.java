package com.campus.Repository.Mail;

import com.campus.Entity.Mail.ReservationNotification;
import com.campus.Entity.Reservation.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationNotificationRepository extends JpaRepository<ReservationNotification,Integer> {
    @Transactional
    List<ReservationNotification> findReservationNotificationsByReservation(Reservation reservation);
    @Query("select reservationNotification from ReservationNotification reservationNotification "+
            "where  (reservationNotification.notificationTime <= :currentTime)  "+
            "and    (reservationNotification.status = 1)"
    )   // 1 in status is referred to pending
    List<ReservationNotification> findReservationNotificationsNotSend(@Param("currentTime")LocalDateTime currentTime);
}
