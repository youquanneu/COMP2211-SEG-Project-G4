package com.campus.Entity.Reservation;

import com.campus.Classification.Approval;
import com.campus.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@Entity
public class Booking {
    public Booking(){}
    public Booking(User booker, List<Reservation> reservations, Approval approval){
        setBooker(booker);
        setReservations(reservations);
        setApproval(approval);
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
}
