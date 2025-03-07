package com.campus.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User booker;
    @OneToOne
    private Resource resource;
    private LocalDateTime reservationStarting;
    private LocalDateTime reservationEnding;

}
