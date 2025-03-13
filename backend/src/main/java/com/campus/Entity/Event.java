package com.campus.Entity;

import com.campus.Entity.Resource.Venue;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Event {
    public Event(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDateTime eventStarting;
    private LocalDateTime eventEnding;
    private String eventDescription;
    @ManyToMany
    private List<Venue> venues;
}
