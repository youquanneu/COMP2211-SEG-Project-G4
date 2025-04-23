package com.campus.DataTransferObject.Reservation;

import com.campus.Entity.Reservation.TimeSlot;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    public static List<TimeSlotDTO> mapper(List<TimeSlot> timeSlots){
        List<TimeSlotDTO> timeSlotDTOS = new ArrayList<>();
        for (TimeSlot timeSlot : timeSlots){
            timeSlotDTOS.add(new TimeSlotDTO(timeSlot.getStartingTime(),timeSlot.getEndingTime()));
        }
        return timeSlotDTOS;
    }
}
