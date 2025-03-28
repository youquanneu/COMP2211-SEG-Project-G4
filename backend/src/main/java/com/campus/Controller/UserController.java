package com.campus.Controller;

import com.campus.Classification.Restriction;
import com.campus.Classification.Status;
import com.campus.Classification.UserRole;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.Equipment;
import com.campus.Entity.Resource.IndoorVenue;
import com.campus.Entity.Resource.OutdoorVenue;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.AdministrativeStaff;
import com.campus.Entity.User.Lecturer;
import com.campus.Entity.User.Student;
import com.campus.Entity.User.User;
import com.campus.Repository.Reservation.ReservationRepository;
import com.campus.Service.Reservation.ReservationService;
import com.campus.Service.Resource.EquipmentService;
import com.campus.Service.Resource.IndoorVenueService;
import com.campus.Service.Resource.ResourceService;
import com.campus.Service.User.AdministrativeStaffService;
import com.campus.Service.User.UserService;
import org.jetbrains.annotations.TestOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@RestController
@RequestMapping("/user")
public class UserController implements CommandLineRunner{
    @Override
    public void run(String... args) throws Exception {
        testNewReservation();
    }
    @Autowired
    private AdministrativeStaffService administrativeStaffService;
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ReservationService reservationService;
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
