package com.campus.DataTransferObject.Event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterForEventRequest {
    public RegisterForEventRequest(){}
    public RegisterForEventRequest(String email,EventDTO eventDTO){
        setEmail(email);
        setEventDTO(eventDTO);
    }
    @JsonProperty
    private String email;
    @JsonProperty
    private EventDTO eventDTO;
    public String getEmail() {
        return email;
    }
    public EventDTO getEventDTO() {
        return eventDTO;
    }
    private void setEmail(String email) {
        this.email = email;
    }
    private void setEventDTO(EventDTO eventDTO) {
        this.eventDTO = eventDTO;
    }
}
