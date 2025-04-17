package com.campus.DataTransferObject.Reservation;

import com.campus.Classification.Approval;
import com.campus.DataTransferObject.Resource.ResourceDTO;
import com.campus.DataTransferObject.User.UserDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationDTO {
    public ReservationDTO(){}
    public ReservationDTO(UserDTO booker, List<ResourceDTO> resources,
                          LocalDateTime reservationStarting, LocalDateTime reservationEnding,
                          Approval approval){
        setBooker(booker);
        setResources(resources);
        setReservationStarting(reservationStarting);
        setReservationEnding(reservationEnding);
        setApproval(approval);
    }
    private UserDTO booker;
    private List<ResourceDTO> resources;
    private LocalDateTime reservationStarting;
    private LocalDateTime reservationEnding;
    private Approval approval;
    public UserDTO getBooker() {
        return booker;
    }
    public List<ResourceDTO> getResources() {
        return resources;
    }
    public LocalDateTime getReservationStarting() {
        return reservationStarting;
    }
    public LocalDateTime getReservationEnding() {
        return reservationEnding;
    }
    public Approval getApproval() {
        return approval;
    }
    private void setBooker(UserDTO booker) {
        this.booker = booker;
    }
    private void setResources(List<ResourceDTO> resources) {
        this.resources = resources;
    }
    private void setReservationStarting(LocalDateTime reservationStarting) {
        this.reservationStarting = reservationStarting;
    }
    private void setReservationEnding(LocalDateTime reservationEnding) {
        this.reservationEnding = reservationEnding;
    }
    private void setApproval(Approval approval) {
        this.approval = approval;
    }
}
