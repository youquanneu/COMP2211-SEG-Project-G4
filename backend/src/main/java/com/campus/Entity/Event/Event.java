package com.campus.Entity.Event;

import com.campus.Entity.Resource.Venue;
import com.campus.Entity.User.User;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event {
    public Event(){}
    public Event(String eventTitle,
                 LocalDateTime eventStarting,
                 LocalDateTime eventEnding,
                 String eventDescription,
                 List<Venue> venues,
                 List<User> organizer){
        setEventTitle(eventTitle);
        checkStartAndEndTime(eventStarting,eventEnding);
        setEventStarting(eventStarting);
        setEventEnding(eventEnding);
        setEventDescription(eventDescription);
        setVenues(venues);
        setOrganizer(organizer);
        setParticipant(new ArrayList<>());
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;
    @NotNull
    private String eventTitle;
    @NotNull
    private LocalDateTime eventStarting;
    @NotNull
    private LocalDateTime eventEnding;
    private String eventDescription;
    @ManyToMany
    private List<Venue> venues;
    @ManyToMany
    private List<User> organizer;
    @ManyToMany
    private List<User> participant;
    public void addParticipant(User user){
        this.participant.add(user);
    }
    public void deleteParticipant(User user){
        this.participant.remove(user);
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
    public List<User> getOrganizer() {
        return organizer;
    }
    public List<User> getParticipant() {
        return participant;
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
    private void setOrganizer(List<User> organizer) {
        this.organizer = organizer;
    }
    private void setParticipant(List<User> participant) {
        this.participant = participant;
    }
    private void checkStartAndEndTime(LocalDateTime startTime, LocalDateTime endingTime){
        if (startTime==null || endingTime == null || startTime.isAfter(endingTime)){
            throw new RuntimeException("Time shouldn't be null or end before start");
        }
    }
}
