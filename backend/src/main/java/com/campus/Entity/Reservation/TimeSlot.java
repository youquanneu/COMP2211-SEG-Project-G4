package com.campus.Entity.Reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

@Entity
public class TimeSlot {
    public TimeSlot(){}
    public TimeSlot(LocalTime startingTime, LocalTime endingTime){
        setStartingTime(startingTime);
        setEndingTime(endingTime);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer timeSlotId;
    @NotNull
    private LocalTime startingTime;
    @NotNull
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
