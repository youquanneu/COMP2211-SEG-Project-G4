package com.campus.Controller;

import com.campus.Entity.Resource.Equipment;
import com.campus.Repository.Resource.ResourceRepository;
import com.campus.Service.Resource.EquipmentService;
import com.campus.Service.User.AdministrativeStaffService;
import com.campus.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
    private ResourceRepository resourceRepository;
    @Override
    public void run(String... args) throws Exception {
        equipmentService.addEquipment();
    }
}
