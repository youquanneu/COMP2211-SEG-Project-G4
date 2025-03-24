package com.campus.Controller;

import com.campus.Service.Reservation.ReservationService;
import com.campus.Service.Resource.EquipmentService;
import com.campus.Service.Resource.IndoorVenueService;
import com.campus.Service.Resource.ResourceService;
import com.campus.Service.User.AdministrativeStaffService;
import com.campus.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
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
    private IndoorVenueService indoorVenueService;
    @Override
    public void run(String... args) throws Exception {
        reservationService.createNewReservation();
    }
}
