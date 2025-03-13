package com.campus.Controller;

import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Equipment;
import com.campus.Entity.Resource.IndoorVenue;
import com.campus.Entity.Resource.Venue;
import com.campus.Repository.Resource.ResourceRepository;
import com.campus.Service.Resource.EquipmentService;
import com.campus.Service.Resource.ResourceService;
import com.campus.Service.User.AdministrativeStaffService;
import com.campus.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
    @Override
    public void run(String... args) throws Exception {
        Venue venue = new Venue("Basketball",LocalTime.NOON,LocalTime.MAX,Restriction.NonRestriction,ResourceCategory.OutdoorVenue);
        IndoorVenue indoorVenue = new IndoorVenue("3r002","Lab",LocalTime.MIN,LocalTime.MIDNIGHT,Restriction.ApprovalRequired);
        resourceService.saveResource(venue);
        resourceService.saveResource(indoorVenue);
    }
}
