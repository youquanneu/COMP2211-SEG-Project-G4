package com.campus.DataTransferObject.Reservation;

import com.campus.DataTransferObject.Resource.ResourceDTO;
import com.campus.DataTransferObject.User.UserDTO;
import com.campus.Entity.Reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDTO {
    public ReservationDTO(){}
    public ReservationDTO(Integer reservationId,
                          UserDTO userDTO,
                          ResourceDTO resourceDTO,
                          LocalDateTime reservationStarting,
                          LocalDateTime reservationEnding){
        setReservationId(reservationId);
        setUserDTO(userDTO);
        setResourceDTO(resourceDTO);
        setReservationStarting(reservationStarting);
        setReservationEnding(reservationEnding);
    }
    public static ReservationDTO mapper(Reservation reservation){
        return new ReservationDTO(
                reservation.getReservationId(),
                UserDTO.mapper(reservation.getBooker()),
                ResourceDTO.mapper(reservation.getResource()),
                reservation.getReservationStarting(),
                reservation.getReservationEnding()
                );
    }
    public static List<ReservationDTO> listMapper(List<Reservation> reservations){
        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        for (Reservation reservation: reservations){
            reservationDTOS.add(mapper(reservation));
        }
        return reservationDTOS;
    }
    @JsonProperty
    private Integer reservationId;
    @JsonProperty
    private UserDTO userDTO;
    @JsonProperty
    private ResourceDTO resourceDTO;
    @JsonProperty
    private LocalDateTime reservationStarting;
    @JsonProperty
    private LocalDateTime reservationEnding;
    public Integer getReservationId() {
        return reservationId;
    }
    public UserDTO getUserDTO() {
        return userDTO;
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
    private void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
    private void setResourceDTO(ResourceDTO resourceDTO) {
        this.resourceDTO = resourceDTO;
    }
    private void setReservationStarting(LocalDateTime reservationStarting) {
        this.reservationStarting = reservationStarting;
    }
    private void setReservationEnding(LocalDateTime reservationEnding) {
        this.reservationEnding = reservationEnding;
    }

}
