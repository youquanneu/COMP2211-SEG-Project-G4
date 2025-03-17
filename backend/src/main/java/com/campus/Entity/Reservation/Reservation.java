package com.campus.Entity.Reservation;

import com.campus.Classification.Approval;
import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
public class Reservation {
    public Reservation(){}
    public Reservation(User booker,List<Resource> resources,
                       LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        setBooker(booker);
        setResources(resources);
        setReservationStarting(reservationStarting);
        setReservationEnding(reservationEnding);
        setApproval(checkResources(resources));
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;
    @ManyToOne
    @JoinColumn(name = "user_Id",nullable = false)
    private User booker;
    @ManyToMany
    @NotNull
    private List<Resource> resources;
    @NotNull
    private LocalDateTime reservationStarting;
    @NotNull
    private LocalDateTime reservationEnding;
    @NotNull
    @JsonProperty("Approval")
    private Approval approval;
    public void changeResources(List<Resource> resources){
        setResources(resources);
    }
    public void changeReservationStartingTime(LocalDateTime reservationStarting){
        setReservationStarting(reservationStarting);
    }
    public void changeReservationEndingTime(LocalDateTime reservationEnding){
        setReservationEnding(reservationEnding);
    }
    public Integer getReservationId() {
        return reservationId;
    }
    public User getBooker() {
        return booker;
    }
    public List<Resource> getResources() {
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
    private void setBooker(User booker) {
        this.booker = booker;
    }
    private void setResources(List<Resource> resources) {
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
    private Approval checkResources(@NotNull List<Resource> resources){
        for (Resource resource: resources) {
            if (resource.getRestriction().equals(Restriction.Restricted)) {
                return Approval.Rejected;
            }
        }      // Prevent user accidentally book restricted resource
        for (Resource resource: resources){
            if (resource.getRestriction().equals(Restriction.ApprovalRequired)) {
                return Approval.Pending;
            }
        }   // Approval required if any approval required resource is booked
        return Approval.Approved;   // Else approve the reservation
    }   // Determine the approval status of reservation
}
