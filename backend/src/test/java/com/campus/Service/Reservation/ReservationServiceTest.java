package com.campus.Service.Reservation;

import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.User;
import com.campus.Service.Resource.ResourceService;
import com.campus.Service.User.UserService;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ReservationServiceTest {
    private ReservationService reservationService;
    private UserService userService;
    private ResourceService resourceService;
    @Test
    public void testNewReservation(){
        for (Reservation reservation : reservationsTestList()){
            reservationService.saveReservation(reservation);
        }
    }
    private List<Reservation> reservationsTestList(){
        List<Reservation> reservations =new ArrayList<>();
        Reservation reservation1 = reservationService.createNewReservation(
                userService.getUserById(1),
                resourceService.getResourceByID(2),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1));
        Reservation reservation2 = reservationService.createNewReservation(
                userService.getUserById(1),
                resourceService.getResourceByID(6),
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusWeeks(1));
        Reservation reservation3 = reservationService.createNewReservation(
                userService.getUserById(1),
                resourceService.getResourceByID(3),
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1));
        Reservation reservation4 = reservationService.createNewReservation(
                userService.getUserById(4),
                resourceService.getResourceByID(2),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1));
        Reservation reservation5 = reservationService.createNewReservation(
                userService.getUserById(4),
                resourceService.getResourceByID(2),
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusWeeks(1));
        reservations.add(reservation1);
        reservations.add(reservation2);
        reservations.add(reservation3);
        reservations.add(reservation4);
        reservations.add(reservation5);
        reservations.removeIf(Objects::isNull);
        return reservations;
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
            return reservationService.saveReservation(reservationService.createNewReservation(user,resource, reservationStarting, reservationEnding));
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
            System.out.println(reservationService.getMyReservationList(user));
            System.out.println("Input reservation Id : ");
            Reservation reservation = reservationService.getReservationById(scanner.nextInt());
            String removeBlank = scanner.nextLine();
            System.out.println("Input Start: yyyy-mm-ddTHH:mm:ss");
            String startingTime = scanner.nextLine();
            LocalDateTime reservationStarting = LocalDateTime.parse(startingTime);
            System.out.println("Input End: yyyy-mm-ddTHH:mm:ss");
            String endingTime = scanner.nextLine();
            LocalDateTime reservationEnding = LocalDateTime.parse(endingTime);
            reservationService.checkEditValidation(user,reservation);
            return reservationService.saveReservation(reservationService.rescheduleReservation(reservation, reservationStarting, reservationEnding));
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
            System.out.println(reservationService.getMyReservationList(user));
            System.out.println("Input reservation Id : ");
            Reservation reservation = reservationService.getReservationById(scanner.nextInt());
            reservationService.checkEditValidation(user,reservation);
            System.out.println("Confirmation : 1.Confirm 2.Cancel");
            if (scanner.nextInt()==1){
                System.out.println(reservationService.cancelReservation(reservation)+ "\nSuccessfully cancelled");
            }else {
                System.out.println("Operation cancel");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            cancelCurrentReservation();
        }
    }   // Demonstration Method : Cancel a reservation
}
