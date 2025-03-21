package com.campus.Service.Reservation;

import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.Resource;
import com.campus.Repository.Reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    public Reservation saveReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }
    private void checkReservation(Resource resource, LocalDateTime starting, LocalDateTime ending){
        checkTimeValidity(starting,ending);
        checkTimeAvailability(resource,starting,ending);
        List<Reservation> reservations = reservationRepository.findReservationByResources(resource);
        for (Reservation reservation : reservations){
            checkTimeConflict(reservation,starting,ending);
        }
    }   // Check if the reservation is valid and doesn't conflict with existing reservations
    private void checkTimeValidity(LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        if (reservationStarting == null || reservationEnding == null){
            throw new RuntimeException("Time of reservation cannot be null");
        }   // Prevent null time in reservation
        if (reservationStarting.isAfter(reservationEnding)){
            throw new RuntimeException("Starting time cannot be after ending time");
        }   // Prevent starting time set after ending time
    }   //  Check if time input is valid
    private void checkTimeAvailability(Resource resource, LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        LocalTime startingTime  = reservationStarting.toLocalTime();
        LocalTime endingTime    = reservationEnding.toLocalTime();
        boolean isStartingTimeUnavailable =
                startingTime.isBefore(resource.getOpenTime()) ||
                        startingTime.isAfter(resource.getCloseTime());  // Check if starting time is outside opening hours
        boolean isEndingTimeUnavailable =
                endingTime.isBefore(resource.getOpenTime()) ||
                        endingTime.isAfter(resource.getCloseTime());    // Check if ending time is outside opening hours
        if (isStartingTimeUnavailable || isEndingTimeUnavailable) {
            throw new RuntimeException("Resource not available during the requested period.");
        }
    }   // Check if the reservation falls within available time
    private void checkTimeConflict(Reservation reservation, LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        boolean isStartingTimeConflict =
                reservationStarting.isAfter(reservation.getReservationStarting()) &&
                        reservationStarting.isBefore(reservation.getReservationEnding());   // Check if starting time conflicts with an existing reservation
        boolean isEndingTimeConflict =
                reservationEnding.isAfter(reservation.getReservationStarting()) &&
                        reservationEnding.isBefore(reservation.getReservationEnding());     // Check if ending time conflicts with an existing reservation
        if (isStartingTimeConflict || isEndingTimeConflict) {
            throw new RuntimeException("The requested reservation time conflicts with an existing reservation.");
        }
    }   // Function : Check if reserve conflict with existing reservation
    private void changeReservationPeriod(Reservation reservation, LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        checkReservation(reservation.getResources(),reservationStarting,reservationEnding);
    }
}
