package com.campus.DataTransferObject.Reservation;

import com.campus.Classification.Approval;
import com.campus.DataTransferObject.User.UserDTO;
import com.campus.Entity.Reservation.Reservation;

import java.util.List;

public class BookingDTO {
    private UserDTO booker;
    private List<Reservation> reservations;
    private Approval approval;

}
