package com.campus.Service.Mail;

import com.campus.Entity.Event.Event;
import com.campus.Entity.Mail.EventNotification;
import com.campus.Entity.Mail.ReservationNotification;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.User.User;
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
        List<ReservationNotification> reservationNotifications =
                reservationNotificationRepository.findReservationNotificationsNotSend(LocalDateTime.now());
        for (ReservationNotification reservationNotification : reservationNotifications){
            emailSenderService.sendReservationReminder(reservationNotification);
            reservationNotification.notificationSend();
            reservationNotificationRepository.save(reservationNotification);
        }
    }
    public void generateEventNotification(Event event, User participant){
        EventNotification eventNotification =
                new EventNotification(participant, event,
                        event.getEventStarting().minusHours(3));
        eventNotificationRepository.save(eventNotification);
    }
    public void cancelledEventNotification(Event event){
        List<EventNotification> eventNotifications =
                eventNotificationRepository.findEventNotificationByEvent(event);
        for (EventNotification eventNotification : eventNotifications){
            eventNotification.notificationCancelled();
            eventNotificationRepository.save(eventNotification);
        }
    }
    public void rescheduleEventNotification(Event event){
        cancelledEventNotification(event);
        List<EventNotification> eventNotifications =
                eventNotificationRepository.findEventNotificationByEvent(event);
        for (EventNotification eventNotification : eventNotifications){
            generateEventNotification(event,eventNotification.getRecipient());
        }
    }
    public void sendEventNotification(){
        List<EventNotification> eventNotifications =
                eventNotificationRepository.findEventNotificationsNotSend(LocalDateTime.now());
        for (EventNotification eventNotification : eventNotifications){
            emailSenderService.sendEventReminder(eventNotification);
            eventNotification.notificationSend();
            eventNotificationRepository.save(eventNotification);
        }
    }
    public void notifyEventCancelled(Event event){
        cancelledEventNotification(event);
        for (User participant : event.getParticipant()){
            emailSenderService.sendEventCancelNews(participant,event);
        }
    }
}
