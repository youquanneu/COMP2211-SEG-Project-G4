package com.campus.Controller.User;

import com.campus.Entity.User.User;
import com.campus.Service.User.AdministrativeStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = Logger.getLogger(AdminController.class.getName());
    @Autowired
    private AdministrativeStaffService administrativeStaffService;
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return administrativeStaffService.registerNewUser(user);
    }
    @PostMapping("/userManagement/registerUser")
    public ResponseEntity<?> registerUser() {
        logger.info("processing : " );
        try {
            return ResponseEntity.ok("");
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("")
    public ResponseEntity<?> booking() {
        logger.info("processing : " );
        try {
            return ResponseEntity.ok("");
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
