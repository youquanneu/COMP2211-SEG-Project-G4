package com.campus.Entity.Mail;

import com.campus.Entity.Reservation.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
public class ReservationNotification extends Notification{
    public ReservationNotification(){}
    public ReservationNotification(Reservation reservation,
                                   LocalDateTime notificationTime){
        super(reservation.getBooker(),notificationTime);
        setReservation(reservation);
    }
    @NotNull
    @ManyToOne
    private Reservation reservation;
    public Reservation getReservation() {
        return reservation;
    }
    private void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
    public String toString(){
        return String.format(
                """
                        Good day, %s
                        Your have a reservation of
                        %s
                        from %s to %s
                        This is reminder for you.
                        Thank you.
                        """,
                getRecipient().getUsername(),
                getReservation().getResource(),
                getReservation().getReservationStarting(),
                getReservation().getReservationEnding());
    }
}
