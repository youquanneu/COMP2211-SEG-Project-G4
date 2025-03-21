package com.campus.Entity.Reservation;

import com.campus.Classification.Approval;
import com.campus.Classification.Restriction;
import com.campus.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@Entity
public class Booking {
    public Booking(){}
    public Booking(User booker, List<Reservation> reservations){
        setBooker(booker);
        setReservations(reservations);
        setApproval(checkResources(reservations));
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;
    @ManyToOne
    @JoinColumn(name = "user_Id",nullable = false)
    private User booker;
    @NotNull
    @JsonProperty("Approval")
    private Approval approval;
    @OneToMany
    private List<Reservation> reservations;
    public Integer getBookingId() {
        return bookingId;
    }
    public User getBooker() {
        return booker;
    }
    public Approval getApproval() {
        return approval;
    }
    public List<Reservation> getReservations() {
        return reservations;
    }
    private void setBooker(User booker) {
        this.booker = booker;
    }
    private void setApproval(Approval approval) {
        this.approval = approval;
    }
    private void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    private Approval checkResources(@NotNull List<Reservation> reservations){
        for (Reservation reservation : reservations) {
            if (reservation.getResources().getRestriction().equals(Restriction.Restricted)) {
                return Approval.Rejected;
            }
        }      // Prevent user accidentally book restricted resource
        for (Reservation reservation : reservations){
            if (reservation.getResources().getRestriction().equals(Restriction.ApprovalRequired)) {
                return Approval.Pending;
            }
        }   // Approval required if any approval required resource is booked
        return Approval.Approved;   // Else approve the reservation
    }   // Determine the approval status of reservation
}
