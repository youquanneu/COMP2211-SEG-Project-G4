package com.campus.Controller;

import com.campus.Entity.Reservation;
import com.campus.Entity.Resource.Equipment;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.User;
import com.campus.Repository.ReservationRepository;
import com.campus.Repository.Resource.ResourceRepository;
import com.campus.Service.ReservationService;
import com.campus.Service.Resource.EquipmentService;
import com.campus.Service.Resource.ResourceService;
import com.campus.Service.User.AdministrativeStaffService;
import com.campus.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController implements CommandLineRunner{
    @Autowired
    private UserService userService;
    @Autowired
    private AdministrativeStaffService administrativeStaffService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Override
    public void run(String... args) throws Exception {
        User user = userService.getUserById(1);
        System.out.println(user);
        List<Resource> resources = new ArrayList<>();
        Resource r1 = resourceService.getResourceByID(2);
        resources.add(r1);
        System.out.println(resources);
        Resource r2 = resourceService.getResourceByID(7);
        resources.add(r2);
        System.out.println(resources);
        Resource r3 = equipmentService.getEquipmentBySerialNumber("S12");
        resources.add(r3);
        System.out.println(resources);
        Reservation reservation = new Reservation(user,resources,LocalDateTime.now(),LocalDateTime.now().plusHours(10));
        reservationRepository.findAll();
        reservationService.saveReservation(reservation);
    }
}
