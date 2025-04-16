package com.campus.DataTransferObject.Reservation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public class TimeSlotDTO {
    public TimeSlotDTO(){}
    public TimeSlotDTO(LocalTime startingTime, LocalTime endingTime){
        setStartingTime(startingTime);
        setEndingTime(endingTime);
    }
    @JsonProperty
    private LocalTime startingTime;
    @JsonProperty
    private LocalTime endingTime;
    public LocalTime getStartingTime() {
        return startingTime;
    }
    public LocalTime getEndingTime() {
        return endingTime;
    }
    private void setStartingTime(LocalTime startingTime) {
        this.startingTime = startingTime;
    }
    private void setEndingTime(LocalTime endingTime) {
        this.endingTime = endingTime;
    }
}
