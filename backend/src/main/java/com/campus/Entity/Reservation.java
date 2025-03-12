package com.campus.Entity;

import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User booker;
    @OneToMany(mappedBy = "booking",cascade = CascadeType.ALL)
    private List<Resource> resources;
    private LocalDateTime reservationStarting;
    private LocalDateTime reservationEnding;
}
