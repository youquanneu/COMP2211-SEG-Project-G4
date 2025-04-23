package com.campus.Controller.User;

import com.campus.Classification.UserRole;
import com.campus.DataTransferObject.User.RegisterRequest;
import com.campus.DataTransferObject.User.UserDTO;
import com.campus.Entity.User.Lecturer;
import com.campus.Entity.User.Student;
import com.campus.Entity.User.User;
import com.campus.Service.User.AdministrativeStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        logger.info("Processing registerUser " );
        try {
            User newUser;
            if (registerRequest.getUserRole().equals(UserRole.Lecturer)){
                newUser = new Lecturer(registerRequest.getUsername(), registerRequest.getEmail(),"Password");
            }else{
                newUser = new Student(registerRequest.getUsername(), registerRequest.getEmail(), "Password");
            }
            administrativeStaffService.registerNewUser(newUser);
            return ResponseEntity.ok(UserDTO.mapper(newUser));
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/userManagement/getStudent")
    public ResponseEntity<?> getStudent() {
        logger.info("Processing getStudent" );
        try {
            List<UserDTO> userDTOS = UserDTO.listMapper(administrativeStaffService.getUserByUserRole(UserRole.Student));
            logger.info("Get Student : " + userDTOS );
            return ResponseEntity.ok(userDTOS);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/userManagement/getLecturer")
    public ResponseEntity<?> getLecturer() {
        logger.info("Processing getLecturer " );
        try {
            List<UserDTO> userDTOS = UserDTO.listMapper(administrativeStaffService.getUserByUserRole(UserRole.Lecturer));
            logger.info("Get Lecturer : " + userDTOS );
            return ResponseEntity.ok(userDTOS);
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
