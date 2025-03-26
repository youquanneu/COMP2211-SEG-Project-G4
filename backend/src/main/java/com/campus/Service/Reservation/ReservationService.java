package com.campus.Service.Reservation;

import com.campus.Classification.Status;
import com.campus.Classification.UserRole;
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
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserService userService;
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
    public Reservation modifyCurrentReservation(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input user Id : ");
            User user = userService.getUserById(scanner.nextInt());
            System.out.println(getMyReservationList(user));
            System.out.println("Input reservation Id : ");
            Reservation reservation = getReservationById(scanner.nextInt());
            String removeBlank = scanner.nextLine();
            System.out.println("Input Start: yyyy-mm-ddTHH:mm:ss");
            String startingTime = scanner.nextLine();
            LocalDateTime reservationStarting = LocalDateTime.parse(startingTime);
            System.out.println("Input End: yyyy-mm-ddTHH:mm:ss");
            String endingTime = scanner.nextLine();
            LocalDateTime reservationEnding = LocalDateTime.parse(endingTime);
            return saveReservation(changeReservationTime(reservation, reservationStarting, reservationEnding));
        }catch (Exception e){
            System.out.println(e.getMessage());
            modifyCurrentReservation();
            return null;
        }
    }   // Demonstration Method : Change current reservation
    public void cancelCurrentReservation(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input user Id : ");
            User user = userService.getUserById(scanner.nextInt());
            System.out.println(getMyReservationList(user));
            System.out.println("Input reservation Id : ");
            Reservation reservation = getReservationById(scanner.nextInt());
            checkCancellationValidation(reservation,user);
            System.out.println("Confirmation : 1.Confirm 2.Cancel");
            if (scanner.nextInt()==1){
                System.out.println(cancelReservation(reservation)+ "\nSuccessfully cancelled");
            }else {
                System.out.println("Operation cancel");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            cancelCurrentReservation();
        }
    }   // Demonstration Method : Cancel a reservation
    public Reservation createNewReservation(User user, Resource resource,
                                            LocalDateTime reservationStarting,
                                            LocalDateTime reservationEnding){
        newReservationValidation(resource,reservationStarting,reservationEnding);
        return new Reservation(user,resource,reservationStarting,reservationEnding);
    }   // Function : Create a new reservation after check the time validation
    public Reservation changeReservationTime(Reservation reservation,
                                      LocalDateTime reservationStarting,
                                      LocalDateTime reservationEnding){
        changeReservationValidation(reservation,reservationStarting,reservationEnding);
        reservation.changeReservationStartingTime(reservationStarting);
        reservation.changeReservationEndingTime(reservationEnding);
        reservation.initializeStatus();
        return reservationRepository.save(reservation);
    }   // Function : Change reservation's period after check the time validation
    public void checkCancellationValidation(Reservation reservation, User booker){
        boolean isAdministrator = booker.getUserRole().equals(UserRole.AdministrativeStaff);
        boolean isInitialBooker = Objects.equals(reservation.getBooker().getUserId(), booker.getUserId());
        if (!(isAdministrator||isInitialBooker)){
            throw new RuntimeException("You are not allowed to cancel the reservation");
        }
    }   // Function : Prevent users cancel a reservation not belongs to them
    public Reservation cancelReservation(Reservation reservation){
        if (reservation.getStatus().equals(Status.Rejected)){
            throw new RuntimeException("Cancellation of a rejected reservation is not allowed");
        }
        reservation.changeReservationStatus(Status.Cancelled);
        return reservationRepository.save(reservation);
    }   // Function : Cancel an active or pending approved reservation
    private void newReservationValidation(Resource resource, LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        checkTimeValidity(reservationStarting,reservationEnding);
        checkTimeAvailability(resource,reservationStarting,reservationEnding);
        checkNewReservationConflict(resource,reservationStarting,reservationEnding);
    }   // Check if the reservation is valid and doesn't conflict with existing reservations
    private void changeReservationValidation(Reservation reservation,LocalDateTime reservationStarting, LocalDateTime reservationEnding){
        checkTimeValidity(reservationStarting,reservationEnding);
        checkTimeAvailability(reservation.getResource(),reservationStarting,reservationEnding);
        checkReservationChangesConflict(reservation,reservationStarting,reservationEnding);
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
    private void checkReservationChangesConflict(Reservation reservation, LocalDateTime reservationStarting, LocalDateTime reservationEnding){
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
}


