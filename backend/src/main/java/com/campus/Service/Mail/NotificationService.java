package com.campus.Service.Mail;

import com.campus.Entity.Mail.ReservationNotification;
import com.campus.Entity.Reservation.Reservation;

import java.time.LocalDateTime;

public class NotificationService {
    private void reservationNotificationOnTime(Reservation reservation){
        ReservationNotification reservationNotification = new ReservationNotification(reservation,reservation.getReservationStarting());
    }
    private void reservationNotificationBefore(Reservation reservation,Integer minutes){
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(minutes);
    }
    private void generateEventNotification(){}
}
