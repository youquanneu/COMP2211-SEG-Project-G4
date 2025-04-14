package com.campus.Controller;

import com.campus.DataTransferObject.Main.EmailDTO;
import com.campus.DataTransferObject.Main.OneTimePasswordDTO;
import com.campus.DataTransferObject.User.LoginRequest;
import com.campus.DataTransferObject.User.UserDTO;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Service.Event.EventService;
import com.campus.Service.Mail.EmailSenderService;
import com.campus.Service.Mail.NotificationService;
import com.campus.Service.Mail.OneTimePasswordService;
import com.campus.Service.Reservation.ReservationService;
import com.campus.Service.Resource.ResourceService;
import com.campus.Service.Resource.VenueService;
import com.campus.Service.User.AdministrativeStaffService;
import com.campus.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserController implements CommandLineRunner{
    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    @Override
    public void run(String... args) throws Exception {
    }
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private OneTimePasswordService oneTimePasswordService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Login processing : " + loginRequest);
        try {
            UserDTO userDTO = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            logger.info("Get userDTO" + userDTO.getUsername());
            return ResponseEntity.ok(userDTO);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PostMapping("/getOtp")
    public ResponseEntity<?> requestOTP(@RequestBody EmailDTO emailDTO){
        logger.info("Sending OTP to : " + emailDTO);
        try {
            String prefixToShow = emailSenderService.sendOTP(emailDTO.getEmail());
            logger.info("OTP with Prefix : " + prefixToShow + " successfully sent to " + emailDTO);
            return ResponseEntity.ok(prefixToShow);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/matchOtp")
    public ResponseEntity<?> matchOTP(@RequestBody OneTimePasswordDTO oneTimePasswordDTO){
        logger.info("Matching OTP : " + oneTimePasswordDTO.getEmail() + " , " + oneTimePasswordDTO.getOtpPrefix() + "-" + oneTimePasswordDTO.getEnteredOTP());
        try {
            UserDTO userDTO = oneTimePasswordService.matchOneTimePassword(
                    oneTimePasswordDTO.getEmail(),
                    oneTimePasswordDTO.getOtpPrefix(),
                    oneTimePasswordDTO.getEnteredOTP());
            logger.info("Log in successful for : " + userDTO.getUsername());
            return ResponseEntity.ok(userDTO);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


    @Autowired
    private AdministrativeStaffService administrativeStaffService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private VenueService venueService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private EventService eventService;
    @Autowired
    private NotificationService notificationService;

}
