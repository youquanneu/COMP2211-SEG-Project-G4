package com.campus.DataTransferObject.Event;

import com.campus.DataTransferObject.Resource.VenueDTO;
import com.campus.DataTransferObject.User.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class EventDTO {
    public EventDTO(){}
    public EventDTO(List<UserDTO> organizer, String eventTitle,
                    LocalDateTime eventStarting, LocalDateTime eventEnding,
                    String eventDescription, List<VenueDTO> venues){
        setOrganizer(organizer);
        setEventTitle(eventTitle);
        setEventStarting(eventStarting);
        setEventEnding(eventEnding);
        setEventDescription(eventDescription);
        setVenues(venues);
    }
    @JsonProperty
    private List<UserDTO> organizer;
    @JsonProperty
    private String eventTitle;
    @JsonProperty
    private LocalDateTime eventStarting;
    @JsonProperty
    private LocalDateTime eventEnding;
    @JsonProperty
    private String eventDescription;
    @JsonProperty
    private List<VenueDTO> venues;
    public List<UserDTO> getOrganizer() {
        return organizer;
    }
    public String getEventTitle() {
        return eventTitle;
    }
    public LocalDateTime getEventStarting() {
        return eventStarting;
    }
    public LocalDateTime getEventEnding() {
        return eventEnding;
    }
    public String getEventDescription() {
        return eventDescription;
    }
    public List<VenueDTO> getVenues() {
        return venues;
    }
    private void setOrganizer(List<UserDTO> organizer) {
        this.organizer = organizer;
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
    private void setVenues(List<VenueDTO> venues) {
        this.venues = venues;
    }
}
