package com.campus.Entity.Event;

import com.campus.Classification.Status;
import com.campus.Entity.Resource.Venue;
import com.campus.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Event {
    public Event(){}
    public Event(String eventTitle,
                 LocalDateTime eventStarting, LocalDateTime eventEnding,
                 String eventDescription,List<Venue> venues){
        setEventTitle(eventTitle);
        setEventStarting(eventStarting);
        setEventEnding(eventEnding);
        setEventDescription(eventDescription);
        setVenues(venues);
        setApproval(Status.Pending);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;
    @ManyToMany
    private List<User> organizer;
    private String eventTitle;
    private LocalDateTime eventStarting;
    private LocalDateTime eventEnding;
    private String eventDescription;
    @ManyToMany
    private List<Venue> venues;
    @NotNull
    @JsonProperty("Status")
    private Status status;
    public void changeEventTitle(String eventTitle){
        setEventTitle(eventTitle);
    };
    public void changeEventStartingTime(LocalDateTime eventStarting){
        setEventStarting(eventStarting);
    }
    public void changeEventEndingTime(LocalDateTime eventEnding){
        setEventEnding(eventEnding);
    }
    public void changeEventDescription(String eventDescription){
        setEventDescription(eventDescription);
    }
    public void changeEventVenue(List<Venue> venues){
        setVenues(venues);
    }
    public void changeApproval(Status status){
        setApproval(status);
    }
    public Integer getEventId() {
        return eventId;
    }
    public String getEventTitle() {
        return eventTitle;
    }
    public LocalDateTime getEventEnding() {
        return eventEnding;
    }
    public LocalDateTime getEventStarting() {
        return eventStarting;
    }
    public String getEventDescription() {
        return eventDescription;
    }
    public List<Venue> getVenues() {
        return venues;
    }
    public Status getApproval() {
        return status;
    }
    private void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
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
    private void setApproval(Status status) {
        this.status = status;
    }
}
