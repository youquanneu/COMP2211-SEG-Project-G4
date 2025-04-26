package com.campus.DataTransferObject.Event;

import com.campus.DataTransferObject.Resource.VenueDTO;
import com.campus.DataTransferObject.User.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class NewEventRequest {
    public NewEventRequest(){}
    public NewEventRequest(String title,
                           String area,
                           LocalDateTime startingTime,
                           LocalDateTime endingTime,
                           List<VenueDTO> venue,
                           List<UserDTO> organizer,
                           String description){
        setTitle(title);
        setArea(area);
        setStartingTime(startingTime);
        setEndingTime(endingTime);
        setVenue(venue);
        setOrganizer(organizer);
        setDescription(description);
    }
    @JsonProperty
    private String title;
    @JsonProperty
    private String area;
    @JsonProperty
    private LocalDateTime startingTime;
    @JsonProperty
    private LocalDateTime endingTime;
    @JsonProperty
    private List<VenueDTO> venue;
    @JsonProperty
    private List<UserDTO> organizer;
    @JsonProperty
    private String description;
    public String getTitle() {
        return title;
    }
    public String getArea() {
        return area;
    }
    public LocalDateTime getStartingTime() {
        return startingTime;
    }
    public LocalDateTime getEndingTime() {
        return endingTime;
    }
    public List<VenueDTO> getVenue() {
        return venue;
    }
    public List<UserDTO> getOrganizer() {
        return organizer;
    }
    public String getDescription() {
        return description;
    }
    private void setTitle(String title) {
        this.title = title;
    }
    private void setArea(String area) {
        this.area = area;
    }
    public void setStartingTime(LocalDateTime startingTime) {
        this.startingTime = startingTime;
    }
    public void setEndingTime(LocalDateTime endingTime) {
        this.endingTime = endingTime;
    }
    private void setVenue(List<VenueDTO> venue) {
        this.venue = venue;
    }
    public void setOrganizer(List<UserDTO> organizer) {
        this.organizer = organizer;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return String.format(
                """
                        Title         :
                        %s
                        Area          :
                        %s
                        Starting Time :
                        %s
                        Ending Time   :
                        %s
                        Venue(s)      :
                        %s
                        Organizer(s)  :
                        %s
                        Description   :
                        %s
                        """,
                getTitle(),
                getArea(),
                getStartingTime(),
                getEndingTime(),
                getVenue(),
                getOrganizer(),
                getDescription());
    }
}
