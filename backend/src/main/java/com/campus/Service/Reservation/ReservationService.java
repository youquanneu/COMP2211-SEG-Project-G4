package com.campus.Service.Reservation;

import com.campus.Classification.Approval;
import com.campus.Classification.Restriction;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.User;
import com.campus.Repository.Reservation.ReservationRepository;
import com.campus.Service.Resource.ResourceService;
import com.campus.Service.User.UserService;
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
    private UserService userService;
    @Autowired
    private ResourceService resourceService;
    public Reservation saveReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }
    public Reservation createNewReservation(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input user Id : ");
            User user = userService.getUserById(scanner.nextInt());
            System.out.println("Input resource Id : ");
            Resource resource = resourceService.getResourceByID(scanner.nextInt());
            String removeBlank = scanner.nextLine();
            System.out.println("Input Start: yyyy-mm-ddTHH:mm:ss");
            String startingTime = scanner.nextLine();
            LocalDateTime reservationStarting = LocalDateTime.parse(startingTime);
            System.out.println("Input End: yyyy-mm-ddTHH:mm:ss");
            String endingTime = scanner.nextLine();
            LocalDateTime reservationEnding = LocalDateTime.parse(endingTime);
            return saveReservation(createNewReservation(user,resource, reservationStarting, reservationEnding));
        }catch (Exception e){
            System.out.println(e.getMessage());
            createNewReservation();
            return null;
        }
    }   // Demonstration Method : Create a new reservation and add into database
    public Reservation createNewReservation(User user, Resource resource,
                                            LocalDateTime reservationStarting,
                                            LocalDateTime reservationEnding){
        newReservationValidation(resource,reservationStarting,reservationEnding);
        return new Reservation(user,resource,reservationStarting,reservationEnding);
    }   // Function : Create a new reservation after check the time validation
    private void newReservationValidation(Resource resource, LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        checkTimeValidity(reservationStarting,reservationEnding);
        checkTimeAvailability(resource,reservationStarting,reservationEnding);
        checkReservationConflict(resource,reservationStarting,reservationEnding);
    }   // Check if the reservation is valid and doesn't conflict with existing reservations
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
            LocalTime openTime   =  resource.getOpenTime();
            LocalTime closeTime = resource.getCloseTime();
            boolean invalidStartingTime = startingTime.isBefore(openTime)   || startingTime.isAfter(closeTime);  // Check if starting time is outside opening hours
            boolean invalidEndingTime   = endingTime.isBefore(openTime)     || endingTime.isAfter(closeTime);    // Check if ending time is outside opening hours
            if (invalidStartingTime || invalidEndingTime) {
                throw new RuntimeException("Resource not available during the requested period.");
            }
        }
    }   // Check if the reservation falls within available time if the resource have opening period
    private void checkReservationConflict(Resource resource, LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        List<Reservation> conflictReservation = reservationRepository.findConflictReservation
                (
                        resource,
                        reservationStarting,
                        reservationEnding,
                        Approval.Rejected
                );
        if (!conflictReservation.isEmpty()){
            throw new RuntimeException("Reservation with time conflict found");
        }
    }
}


