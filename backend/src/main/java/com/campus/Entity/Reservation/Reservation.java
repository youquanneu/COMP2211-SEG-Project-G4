package com.campus.Entity.Reservation;

import com.campus.Entity.Resource.Resource;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
public class Reservation {
    public Reservation(){}
    public Reservation(Resource resource,
                       LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        setResources(resource);
        setReservationStarting(reservationStarting);
        setReservationEnding(reservationEnding);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;
    @ManyToOne
    @NotNull
    private Resource resources;
    @NotNull
    private LocalDateTime reservationStarting;
    @NotNull
    private LocalDateTime reservationEnding;
    @ManyToOne
    private Booking booking;
    public void changeReservationStartingTime(LocalDateTime reservationStarting){
        setReservationStarting(reservationStarting);
    }
    public void changeReservationEndingTime(LocalDateTime reservationEnding){
        setReservationEnding(reservationEnding);
    }
    public Integer getReservationId() {
        return reservationId;
    }
    public Resource getResources() {
        return resources;
    }
    public LocalDateTime getReservationStarting() {
        return reservationStarting;
    }
    public LocalDateTime getReservationEnding() {
        return reservationEnding;
    }
    private void setResources(Resource resources) {
        this.resources = resources;
    }
    private void setReservationStarting(LocalDateTime reservationStarting) {
        this.reservationStarting = reservationStarting;
    }
    private void setReservationEnding(LocalDateTime reservationEnding) {
        this.reservationEnding = reservationEnding;
    }
}
