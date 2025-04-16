package com.campus.DataTransferObject.Reservation;

import com.campus.Classification.Purpose;
import com.campus.DataTransferObject.Resource.ResourceDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationRequest {
    public ReservationRequest(){}
    public ReservationRequest(String userEmail,
                              ResourceDTO resourceDTO,
                              Purpose purpose,
                              String reservationDate,
                              TimeSlotDTO timeSlotDTO){
        setUserEmail(userEmail);
        setResourceDTO(resourceDTO);
        setPurpose(purpose);
        setReservationDate(reservationDate);
        setTimeSlotDTO(timeSlotDTO);
    }
    @JsonProperty
    private String userEmail;
    @JsonProperty
    private ResourceDTO resourceDTO;
    @JsonProperty("purpose")
    private Purpose purpose;
    @JsonProperty
    private String reservationDate;
    @JsonProperty
    private TimeSlotDTO timeSlotDTO;
    public String getUserEmail() {
        return userEmail;
    }
    public ResourceDTO getResourceDTO() {
        return resourceDTO;
    }
    public Purpose getPurpose() {
        return purpose;
    }
    public LocalDate getReservationDate() {
        return LocalDate.parse(reservationDate);
    }
    public TimeSlotDTO getTimeSlotDTO() {
        return timeSlotDTO;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public void setResourceDTO(ResourceDTO resourceDTO) {
        this.resourceDTO = resourceDTO;
    }
    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }
    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }
    public void setTimeSlotDTO(TimeSlotDTO timeSlotDTO) {
        this.timeSlotDTO = timeSlotDTO;
    }
}
