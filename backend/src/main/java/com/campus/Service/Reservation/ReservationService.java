package com.campus.Service.Reservation;

import com.campus.Classification.Purpose;
import com.campus.Classification.Status;
import com.campus.Classification.UserRole;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Reservation.TimeSlot;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.User;
import com.campus.Repository.Reservation.ReservationRepository;
import com.campus.Service.Mail.NotificationService;
import com.campus.Service.Resource.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Lazy
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private TimeSlotService timeSlotService;
    @Autowired
    private ResourceService resourceService;
    public Reservation getReservationById(Integer reservationId){
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isEmpty()){
            throw new RuntimeException("Reservation not found");
        }
        return reservation.get();
    }
    public Reservation saveReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }
    public List<Reservation> getMyReservationList(User user){
        return reservationRepository.findReservationByBooker(user);
    }
    public Reservation createNewReservation(User user,
                                            Resource resource,
                                            Purpose purpose,
                                            LocalDateTime reservationStarting,
                                            LocalDateTime reservationEnding){
//     try {
            newReservationValidation(resource, reservationStarting, reservationEnding);
            Reservation reservation = new Reservation(user, resource, purpose, reservationStarting, reservationEnding);
            saveReservation(reservation);
            notificationService.reservationNotification(reservation);

            return reservation;
//        }catch (Exception e){
//           return null;
//        }
    }   // Function : Create a new reservation after check the time validation
    void checkEditValidation(User booker, Reservation reservation){
        boolean isAdministrator = booker.getUserRole().equals(UserRole.AdministrativeStaff);
        boolean isInitialBooker = Objects.equals(reservation.getBooker().getUserId(), booker.getUserId());
        if (!(isAdministrator||isInitialBooker)){
            throw new RuntimeException("You are not allowed to edit the reservation");
        }
    }   // Function : Prevent users edit a reservation not belongs to them
    public Reservation rescheduleReservation(Reservation reservation,
                                             LocalDateTime reservationStarting,
                                             LocalDateTime reservationEnding){
        rescheduleReservationValidation(reservation,reservationStarting,reservationEnding);
        reservation.changeReservationTime(reservationStarting, reservationEnding);
        reservation.initializeStatus();
        reservationRepository.save(reservation);
        notificationService.rescheduleReservationNotification(reservation);
        return reservation;
    }   // Function : Change reservation's period after check the time validation
    public Reservation cancelReservation(Reservation reservation){
        if (reservation.getStatus().equals(Status.Rejected)){
            throw new RuntimeException("Cancellation of a rejected reservation is not allowed");
        }
        reservation.changeReservationStatus(Status.Cancelled);
        notificationService.cancelledReservationNotification(reservation);
        return reservationRepository.save(reservation);
    }   // Function : Cancel an active or pending approved reservation
    private void newReservationValidation(Resource resource, LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        checkTimeValidity(reservationStarting,reservationEnding);
        checkTimeAvailability(resource,reservationStarting,reservationEnding);
        checkNewReservationConflict(resource,reservationStarting,reservationEnding);
    }   // Check if the reservation is valid and doesn't conflict with existing reservations
    private void rescheduleReservationValidation(Reservation reservation, LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        checkTimeValidity(reservationStarting,reservationEnding);
        checkTimeAvailability(reservation.getResource(),reservationStarting,reservationEnding);
        checkRescheduleReservationConflict(reservation,reservationStarting,reservationEnding);
    }   // Check if the reservation is valid and doesn't conflict with other existing reservations
    private void checkTimeValidity(LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        if (reservationStarting == null || reservationEnding == null){
            throw new RuntimeException("Time of reservation cannot be null");
        }   // Prevent null time in reservation
        if (reservationStarting.isBefore(LocalDateTime.now()) || reservationEnding.isBefore(LocalDateTime.now())){
            throw new RuntimeException("Reservation time cannot before current time");
        }   // Prevent time set before current time
        if (reservationStarting.isAfter(reservationEnding)){
            throw new RuntimeException("Starting time cannot be after ending time");
        }   // Prevent starting time set after ending time
    }   //  Check if time input is valid
    private void checkTimeAvailability(Resource resource, LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        if (resource.getOpenTime()!=null) {
            if (!reservationStarting.toLocalDate().equals(reservationEnding.toLocalDate())){
                throw new RuntimeException("Periodic open resources cannot booked overnight");
            }   // Check if the reservation spans across different days
            else {
                LocalTime startingTime = reservationStarting.toLocalTime();
                LocalTime endingTime = reservationEnding.toLocalTime();
                LocalTime openTime = resource.getOpenTime();
                LocalTime closeTime = resource.getCloseTime();
                boolean invalidStartingTime = startingTime.isBefore(openTime) || startingTime.isAfter(closeTime);  // Check if starting time is outside opening hours
                boolean invalidEndingTime = endingTime.isBefore(openTime) || endingTime.isAfter(closeTime);    // Check if ending time is outside opening hours
                if (invalidStartingTime || invalidEndingTime) {
                    throw new RuntimeException("Resource not available during the requested period");
                }
            }
        }
    }   // Check if the reservation falls within available time if the resource have opening period
    private void checkNewReservationConflict(Resource resource, LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        List<Reservation> conflictReservation = reservationRepository.filterConflictReservation
                (
                        resource,
                        reservationStarting,
                        reservationEnding
                );
        if (!conflictReservation.isEmpty()){
            throw new RuntimeException("Reservation with time conflict found");
        }
    }   // Check if new reservation conflict with existing reservation
    private void checkRescheduleReservationConflict(Reservation reservation, LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        List<Reservation> conflictReservation = reservationRepository.filterOtherConflictReservation
                (
                        reservation.getReservationId(),
                        reservation.getResource(),
                        reservationStarting,
                        reservationEnding
                );
        if (!conflictReservation.isEmpty()){
            throw new RuntimeException("Reservation with time conflict found");
        }
    }   // Check if reservation after change conflict with other reservation
    public List<TimeSlot> availableTime(Resource resource, LocalDate localDate){
        return timeSlotService.getAvailableTimeSlots(resource,reservationsByDate(resource,localDate));
    }
    private List<Reservation> reservationsByDate(Resource resource, LocalDate localDate){
        LocalDateTime dateStart = localDate.atTime(0,0);
        LocalDateTime dateEnd   = localDate.plusDays(1).atStartOfDay().minusSeconds(1);
        return reservationRepository.filterReservationByDate(resource,dateStart,dateEnd);
    }
}


