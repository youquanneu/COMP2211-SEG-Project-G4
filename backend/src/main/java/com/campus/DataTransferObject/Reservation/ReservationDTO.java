package com.campus.DataTransferObject.Reservation;

import com.campus.DataTransferObject.Resource.ResourceDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationDTO {
    public ReservationDTO(){}
    public ReservationDTO(Integer reservationId, ResourceDTO resourceDTO,
                          LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        setReservationId(reservationId);
        setResourceDTO(resourceDTO);
        setReservationStarting(reservationStarting);
        setReservationEnding(reservationEnding);
    }
    private Integer reservationId;
    private ResourceDTO resourceDTO;
    private LocalDateTime reservationStarting;
    private LocalDateTime reservationEnding;
    public Integer getReservationId() {
        return reservationId;
    }
    public ResourceDTO getResourceDTO() {
        return resourceDTO;
    }
    public LocalDateTime getReservationStarting() {
        return reservationStarting;
    }
    public LocalDateTime getReservationEnding() {
        return reservationEnding;
    }
    private void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }
    private void setReservationStarting(LocalDateTime reservationStarting) {
        this.reservationStarting = reservationStarting;
    }
    private void setReservationEnding(LocalDateTime reservationEnding) {
        this.reservationEnding = reservationEnding;
    }
    private void setResourceDTO(ResourceDTO resourceDTO) {
        this.resourceDTO = resourceDTO;
    }
}
