package com.campus.Service.Reservation;

import com.campus.Classification.Approval;
import com.campus.Classification.Restriction;
import com.campus.Entity.Reservation.Booking;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.User.User;
import com.campus.Repository.Reservation.BookingRepository;
import com.campus.Service.Resource.ResourceService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ReservationService reservationService;
    public Booking saveBooking(Booking booking){
        return bookingRepository.save(booking);
    }
    public Booking getBookingById(Integer bookingId){
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()){
            throw new RuntimeException("Booking not found");
        }
        return booking.get();
    }
    public void newBooking(User user, List<Reservation> reservations){
        Approval approval = checkResources(reservations);
        setReservationsApproval(reservations,approval);
        saveBooking(new Booking(user,reservations,approval));
        if (approval.equals(Approval.Rejected)){
            throw new RuntimeException("Booking not allowed");
        }
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
    private void setReservationsApproval(List<Reservation>reservations, Approval approval){
        for (Reservation reservation: reservations){
            reservation.changeReservationApproval(approval);
            reservationService.saveReservation(reservation);
        }
    }   // Function : Set the approval of the reservation and add into database
}
