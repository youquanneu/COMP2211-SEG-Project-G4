package com.campus.Service.Mail;

import com.campus.Entity.Mail.ReservationNotification;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Repository.Mail.EventNotificationRepository;
import com.campus.Repository.Mail.ReservationNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private EventNotificationRepository eventNotificationRepository;
    @Autowired
    private ReservationNotificationRepository reservationNotificationRepository;
    public void reservationNotification(Reservation reservation){
        ReservationNotification notificationBefore1Hrs =
                new ReservationNotification(reservation,reservation.getReservationStarting().minusHours(1));
        ReservationNotification notificationBefore5Min =
                new ReservationNotification(reservation,reservation.getReservationStarting().minusMinutes(5));
        reservationNotificationRepository.save(notificationBefore1Hrs);
        reservationNotificationRepository.save(notificationBefore5Min);
    }
    public void cancelledReservationNotification(Reservation reservation){
        List<ReservationNotification> reservationNotifications =
                reservationNotificationRepository.findReservationNotificationsByReservation(reservation);
        for (ReservationNotification reservationNotification : reservationNotifications){
            reservationNotification.notificationCancelled();
            reservationNotificationRepository.save(reservationNotification);
        }
    }
    public void rescheduleReservationNotification(Reservation reservation){
        cancelledReservationNotification(reservation);
        reservationNotification(reservation);
    }
    public void sendReservationNotification(){
        List<ReservationNotification> reservationNotifications = reservationNotificationRepository.findReservationNotificationsNotSend(LocalDateTime.now());
        for (ReservationNotification reservationNotification : reservationNotifications){
            emailSenderService.sendReservationReminder(reservationNotification);
            reservationNotification.notificationCancelled();
            reservationNotificationRepository.save(reservationNotification);
        }
    }
    private void generateEventNotification(){}
}
