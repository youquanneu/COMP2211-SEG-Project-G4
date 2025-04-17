package com.campus.DataTransferObject.Event;

import com.campus.DataTransferObject.Resource.VenueDTO;
import com.campus.DataTransferObject.User.UserDTO;
import com.campus.Entity.Event.Event;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventDTO {
    public EventDTO(){}
    public EventDTO(Integer eventId,
                    String eventTitle,
                    String area,
                    LocalDateTime eventStarting,
                    LocalDateTime eventEnding,
                    List<VenueDTO> venues,
                    List<UserDTO> organizer,
                    String eventDescription){
        setEventId(eventId);
        setEventTitle(eventTitle);
        setArea(area);
        setEventStarting(eventStarting);
        setEventEnding(eventEnding);
        setVenues(venues);
        setOrganizer(organizer);
        setEventDescription(eventDescription);
    }
    public static EventDTO mapper(Event event){
        return new EventDTO(
                event.getEventId(),
                event.getEventTitle(),
                event.getArea(),
                event.getEventStarting(),
                event.getEventEnding(),
                VenueDTO.eventListMapper(event.getVenues()),
                UserDTO.listMapper(event.getOrganizer()),
                event.getEventDescription()
                );
    }
    public static List<EventDTO> listMapper(List<Event>events){
        List<EventDTO> eventDTOS = new ArrayList<>();
        for (Event event : events){
            eventDTOS.add(mapper(event));
        }
        return eventDTOS;
    }
    @JsonProperty
    private Integer eventId;
    @JsonProperty
    private String eventTitle;
    @JsonProperty
    private String area;
    @JsonProperty
    private LocalDateTime eventStarting;
    @JsonProperty
    private LocalDateTime eventEnding;
    @JsonProperty
    private List<VenueDTO> venues;
    @JsonProperty
    private List<UserDTO> organizer;
    @JsonProperty
    private String eventDescription;
    public Integer getEventId() {
        return eventId;
    }
    public String getArea() {
        return area;
    }
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
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
    private void setArea(String area) {
        this.area = area;
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
