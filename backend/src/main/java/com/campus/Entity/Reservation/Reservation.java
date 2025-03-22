package com.campus.Entity.Reservation;

import com.campus.Classification.Approval;
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
        setApproval(Approval.Pending);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;
    @NotNull
    @ManyToOne
    private Resource resources;
    @NotNull
    private LocalDateTime reservationStarting;
    @NotNull
    private LocalDateTime reservationEnding;
    @NotNull
    private Approval approval;
    @ManyToOne
    @JoinColumn(name = "bookingId")
    private Booking booking;
    public void changeReservationStartingTime(LocalDateTime reservationStarting){
        setReservationStarting(reservationStarting);
    }
    public void changeReservationEndingTime(LocalDateTime reservationEnding){
        setReservationEnding(reservationEnding);
    }
    public void changeReservationApproval(Approval approval){
        setApproval(approval);
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
    public Approval getApproval() {
        return approval;
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
    private void setApproval(Approval approval) {
        this.approval = approval;
    }
    public String toString(){
        return String.format(
                """
                        Id              : %s
                        Resources       : %s
                        Starting Time   : %s
                        Ending Time     : %s
                        """,
                getReservationId(),getResources().getResourceName(),
                getReservationStarting(),getReservationEnding());
    }
}
