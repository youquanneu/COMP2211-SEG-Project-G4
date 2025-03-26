package com.campus.Entity.Reservation;

import com.campus.Classification.Status;
import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.User;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
public class Reservation {
    public Reservation(){}
    public Reservation(User booker, Resource resource,
                       LocalDateTime reservationStarting,
                       LocalDateTime reservationEnding){
        setBooker(booker);
        setResource(resource);
        setReservationStarting(reservationStarting);
        setReservationEnding(reservationEnding);
        setStatus(checkResource(resource));
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;
    @NotNull
    @ManyToOne
    private User booker;
    @NotNull
    @ManyToOne
    private Resource resource;
    @NotNull
    private LocalDateTime reservationStarting;
    @NotNull
    private LocalDateTime reservationEnding;
    @NotNull
    private Status status;
    public void changeReservationStartingTime(LocalDateTime reservationStarting){
        setReservationStarting(reservationStarting);
    }
    public void changeReservationEndingTime(LocalDateTime reservationEnding){
        setReservationEnding(reservationEnding);
    }
    public void initializeStatus(){
        setStatus(checkResource(getResource()));
    }
    public void changeReservationStatus(Status status){
        setStatus(status);
    }
    public Integer getReservationId() {
        return reservationId;
    }
    public User getBooker() {
        return booker;
    }
    public Resource getResource() {
        return resource;
    }
    public LocalDateTime getReservationStarting() {
        return reservationStarting;
    }
    public LocalDateTime getReservationEnding() {
        return reservationEnding;
    }
    public Status getStatus() {
        return status;
    }
    private void setBooker(User user) {
        this.booker = user;
    }
    private void setResource(Resource resources) {
        this.resource = resources;
    }
    private void setReservationStarting(LocalDateTime reservationStarting) {
        this.reservationStarting = reservationStarting;
    }
    private void setReservationEnding(LocalDateTime reservationEnding) {
        this.reservationEnding = reservationEnding;
    }
    private void setStatus(Status status) {
        this.status = status;
    }
    private Status checkResource(Resource resource) {
        Restriction restriction = resource.getRestriction();
        return switch (restriction) {
            case Restricted         -> Status.Rejected;   // Prevent user accidentally book restricted resource
            case ApprovalRequired   -> Status.Pending;    // Pending if resource required approval
            default                 -> Status.Approved;   // Else approve the reservation
        };
    }
    public String toString(){
        return String.format(
                """
                        Reservation Id  : %s
                        Booker          : %s
                        Resource        : %s
                        Starting Time   : %s
                        Ending Time     : %s
                        Status          : %s
                        """,
                getReservationId(),
                getBooker().getUsername(),
                getResource().getResourceName(),
                getReservationStarting(),
                getReservationEnding(),
                getStatus());
    }
}
