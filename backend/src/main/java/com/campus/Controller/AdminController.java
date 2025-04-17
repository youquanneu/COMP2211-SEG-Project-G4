package com.campus.Controller;

import com.campus.Entity.User.User;
import com.campus.Service.User.AdministrativeStaffService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdministrativeStaffService administrativeStaffService;
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return administrativeStaffService.registerNewUser(user);
    }
}
