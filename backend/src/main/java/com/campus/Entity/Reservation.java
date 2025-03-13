package com.campus.Entity;

import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Reservation {
    public Reservation(){}
    public Reservation(User booker, List<Resource> resources,
                       LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        setBooker(booker);
        setResources(resources);
        setReservationStarting(reservationStarting);
        setReservationEnding(reservationEnding);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;
    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private User booker;
    @OneToMany(mappedBy = "booking",cascade = CascadeType.ALL)
    private List<Resource> resources;
    private LocalDateTime reservationStarting;
    private LocalDateTime reservationEnding;
    public Integer getReservationId() {
        return reservationId;
    }
    private void setBooker(User booker) {
        this.booker = booker;
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
    private void setResources(List<Resource> resources) {
        this.resources = resources;
    }
    private void setReservationStarting(LocalDateTime reservationStarting) {
        this.reservationStarting = reservationStarting;
    }
    private void setReservationEnding(LocalDateTime reservationEnding) {
        this.reservationEnding = reservationEnding;
    }

}
