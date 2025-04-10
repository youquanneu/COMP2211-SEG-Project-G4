package com.campus.Controller;

import com.campus.Classification.Restriction;
import com.campus.Classification.UserRole;
import com.campus.DataTransferObject.Mail.EmailDTO;
import com.campus.DataTransferObject.User.LoginRequest;
import com.campus.DataTransferObject.User.UserDTO;
import com.campus.Entity.Event.Event;
import com.campus.Entity.Mail.OneTimePassword;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.*;
import com.campus.Entity.User.AdministrativeStaff;
import com.campus.Entity.User.Lecturer;
import com.campus.Entity.User.Student;
import com.campus.Entity.User.User;
import com.campus.Service.Event.EventService;
import com.campus.Service.Mail.EmailSenderService;
import com.campus.Service.Reservation.ReservationService;
import com.campus.Service.Resource.ResourceService;
import com.campus.Service.Resource.VenueService;
import com.campus.Service.User.AdministrativeStaffService;
import com.campus.Service.User.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSenderService emailSenderService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Run login request body");
        try {
            System.out.println("Try user login service");
            UserDTO userDTO = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            System.out.println("Get userDTO" + userDTO);
            return ResponseEntity.ok(userDTO);
        }catch (Exception e){
            System.out.println("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PostMapping("/getOtp")
    public ResponseEntity<?> requestOTP(@RequestBody EmailDTO emailDTO){
        System.out.println("Send OTP on going : "  + emailDTO.getEmail());
        OneTimePassword oneTimePassword = emailSenderService.sendOTP(emailDTO.getEmail());
        return ResponseEntity.ok(oneTimePassword.getOtpPrefix());
    }
    @PostMapping("/matchOtp")
    public ResponseEntity<?> matchOTP(@RequestBody String email, String otp){
        return ResponseEntity.ok(email + " , " + otp);
    }
    @Autowired
    private AdministrativeStaffService administrativeStaffService;
    @Override
    public void run(String... args) throws Exception {
    }
}
