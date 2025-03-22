package com.campus.Service.Reservation;

import com.campus.Classification.Approval;
import com.campus.Classification.Restriction;
import com.campus.Entity.Reservation.Booking;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Repository.Reservation.BookingRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    public Booking saveBooking(Booking booking){
        return bookingRepository.save(booking);
    }
    private Approval checkResources(@NotNull List<Reservation> reservations){
        for (Reservation reservation : reservations) {
            if (reservation.getResources().getRestriction().equals(Restriction.Restricted)) {
                return Approval.Rejected;
            }
        }      // Prevent user accidentally book restricted resource
        for (Reservation reservation : reservations){
            if (reservation.getResources().getRestriction().equals(Restriction.ApprovalRequired)) {
                return Approval.Pending;
            }
        }   // Approval required if any approval required resource is booked
        return Approval.Approved;   // Else approve the reservation
    }   // Determine the approval status of reservation
}
