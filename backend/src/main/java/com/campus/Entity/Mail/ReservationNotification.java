package com.campus.Entity.Mail;

import com.campus.Entity.Reservation.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class ReservationNotification extends Notification{
    public ReservationNotification(){}
    public ReservationNotification(Reservation reservation,
                                   LocalDateTime notificationTime){
        super(reservation.getBooker(),notificationTime);
        setReservation(reservation);
    }
    @ManyToOne
    private Reservation reservation;
    public Reservation getReservation() {
        return reservation;
    }
    private void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
