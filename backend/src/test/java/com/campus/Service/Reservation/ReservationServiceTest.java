package com.campus.Service.Reservation;

import com.campus.Entity.Reservation.Reservation;
import com.campus.Service.Resource.ResourceService;
import com.campus.Service.User.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReservationServiceTest {
    private ReservationService reservationService;
    private UserService userService;
    private ResourceService resourceService;
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
}
