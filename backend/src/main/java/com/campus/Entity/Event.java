package com.campus.Entity;

import com.campus.Entity.Resource.Venue;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Event {
    public Event(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime eventStarting;
    private LocalDateTime eventEnding;
    private String eventDescription;
    @ManyToMany
    private List<Venue> venues;
    public String getEventDescription() {
        return eventDescription;
    }
    public LocalDateTime getEventEnding() {
        return eventEnding;
    }
    public LocalDateTime getEventStarting() {
        return eventStarting;
    }
    public List<Venue> getVenues() {
        return venues;
    }
    private void setEventStarting(LocalDateTime eventStarting) {
        this.eventStarting = eventStarting;
    }
    private void setEventEnding(LocalDateTime eventEnding) {
        this.eventEnding = eventEnding;
    }
    private void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    private void setVenues(List<Venue> venues) {
        this.venues = venues;
    }
}
