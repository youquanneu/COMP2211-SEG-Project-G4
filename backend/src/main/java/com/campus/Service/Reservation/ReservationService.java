package com.campus.Service.Reservation;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.campus.Classification.Approval;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.Resource;
import com.campus.Repository.Reservation.ReservationRepository;
import com.campus.Service.Resource.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ResourceService resourceService;
    public Reservation saveReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }
    public List<Reservation> getNonRejectedReservation(Resource resource){
        return reservationRepository.findReservationByResourcesAndApprovalIsNot(resource,Approval.Rejected);
    }
    public void createNewReservation(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input Start: yyyy-mm-ddTHH:mm:ss");
            String startingTime = scanner.nextLine();
            LocalDateTime reservationStarting = LocalDateTime.parse(startingTime);
            System.out.println("Input End: yyyy-mm-ddTHH:mm:ss");
            String endingTime = scanner.nextLine();
            LocalDateTime reservationEnding = LocalDateTime.parse(endingTime);
            System.out.println("Input resource Id : ");
            Resource resource = resourceService.getResourceByID(scanner.nextInt());
            newReservationValidation(resource, reservationStarting, reservationEnding);
            saveReservation(new Reservation(resource, reservationStarting, reservationEnding));
        }catch (Exception e){
            System.out.println(e.getMessage());
            createNewReservation();
        }
    }   // Demonstration Method : Create a new reservation and add into database
    public Reservation createNewReservation(Resource resource,
                                            LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        newReservationValidation(resource,reservationStarting,reservationEnding);
        return new Reservation(resource,reservationStarting,reservationEnding);
    }
    private void newReservationValidation(Resource resource, LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        checkTimeValidity(reservationStarting,reservationEnding);
        checkTimeAvailability(resource,reservationStarting,reservationEnding);
        List<Reservation> reservations = reservationRepository.findReservationByResourcesAndApprovalIsNot(resource,Approval.Rejected);
        for (Reservation reservation : reservations){
            checkTimeConflict(reservation,reservationStarting,reservationEnding);
        }
    }   // Check if the reservation is valid and doesn't conflict with existing reservations
    private void changeReservationValidation(Reservation reservation, LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        Resource resource = reservation.getResources();
        checkTimeValidity(reservationStarting,reservationEnding);
        checkTimeAvailability(resource,reservationStarting,reservationEnding);
        List<Reservation> reservations = reservationRepository.findReservationByResourcesAndApprovalIsNot(resource,Approval.Rejected);
        reservations.remove(reservation);   // Prevent self conflict
        for (Reservation otherReservation : reservations){
            checkTimeConflict(otherReservation,reservationStarting,reservationEnding);
        }
    }   // Check if the changes is valid and doesn't conflict with other reservations
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
        }
    }   // Check if the reservation falls within available time if the resource have opening period
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
}
