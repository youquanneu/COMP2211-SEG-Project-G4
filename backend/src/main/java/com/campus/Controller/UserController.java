package com.campus.Controller;

import com.campus.Classification.Restriction;
import com.campus.Classification.Status;
import com.campus.Classification.UserRole;
import com.campus.DataTransferObject.User.LoginRequest;
import com.campus.DataTransferObject.User.UserDTO;
import com.campus.Entity.Event.Event;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.*;
import com.campus.Entity.User.AdministrativeStaff;
import com.campus.Entity.User.Lecturer;
import com.campus.Entity.User.Student;
import com.campus.Entity.User.User;
import com.campus.Repository.Reservation.ReservationRepository;
import com.campus.Service.Event.EventService;
import com.campus.Service.Reservation.ReservationService;
import com.campus.Service.Resource.EquipmentService;
import com.campus.Service.Resource.IndoorVenueService;
import com.campus.Service.Resource.ResourceService;
import com.campus.Service.Resource.VenueService;
import com.campus.Service.User.AdministrativeStaffService;
import com.campus.Service.User.UserService;
import org.jetbrains.annotations.TestOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController implements CommandLineRunner {


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            UserDTO userDTO = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            userService.loadUserByUsername(userDTO.getUsername());
            return ResponseEntity.ok(userDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
//    @PostMapping("/forgot-password")
//    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
//        // Implement logic to handle forgot password (e.g., send OTP via email)
//        System.out.println("Forgot password request for: " + forgotPasswordRequest.getEmail());
//        return ResponseEntity.ok("OTP sent successfully (simulated)");
//    }
//
//    @PostMapping("/verify-otp")
//    public ResponseEntity<?> verifyOTP(@RequestBody OTPVerificationRequest otpVerificationRequest) {
//        // Implement logic to verify the OTP
//        System.out.println("Verifying OTP: " + otpVerificationRequest.getOtp());
//        // You might want to check if the OTP matches the one sent for the user
//        if (otpVerificationRequest.getOtp() != null && otpVerificationRequest.getOtp().equals("123456")) { // Example OTP
//            return ResponseEntity.ok("OTP verified successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
//        }
//    }
//
//    @PostMapping("/reset-password")
//    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
//        // Implement logic to update the user's password
//        System.out.println("Resetting password for user");
//        if (resetPasswordRequest.getNewPassword() != null &&
//                resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword()) &&
//                resetPasswordRequest.getNewPassword().length() >= 6) {
//            return ResponseEntity.ok("Password reset successfully");
//        } else {
//            return ResponseEntity.badRequest().body("Passwords do not match or are too short");
//        }
//    }

//    @GetMapping("/user-data")
//    public ResponseEntity<?> getUserData() {
//        // In a real application, you would likely need authentication to get the current user's data
//        // For now, let's return some sample data
//        UserResponse user = new UserResponse("John Doe", "john.doe@example.com");
//        return ResponseEntity.ok(user);
//    }

    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private VenueService venueService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private EventService eventService;
    @Override
    public void run(String... args) throws Exception {
    }
    @Autowired
    private AdministrativeStaffService administrativeStaffService;
    public void testRegisterUser(){
        List<User> userForRegister = userListForRegisterTest();
        for (User user : userForRegister){
            administrativeStaffService.registerNewUser(user);
        }
    }
    private List<User> userListForRegisterTest(){
        List<User> userList = new ArrayList<>();
        User student1 = new Student("student1","student1@gmail.com","passwordStd1");
        User student2 = new Student("student2","student2@gmail.com","passwordStd2");
        User student3 = new Student("student3","student3@gmail.com","passwordStd3");
        User lecturer1 = new Lecturer("lecturer1","lecturer1@gmail.com","passwordLec1");
        User lecturer2 = new Lecturer("lecturer2","lecturer2@gmail.com","passwordLec2");
        User administrativeStaff = new AdministrativeStaff("Admin1","Admin1@gmail.com","adminPassword");
        userList.add(student1);
        userList.add(student2);
        userList.add(student3);
        userList.add(lecturer1);
        userList.add(lecturer2);
        userList.add(administrativeStaff);
        return userList;
    }
    public void testAddNewResource(){
        for (Resource resource: testResourceList()) {
            administrativeStaffService.addNewResource(resource  );
        }
    }   // Demonstration method : Add a new resource
    private List<Resource> testResourceList(){
        List<Resource> resourceList = new ArrayList<>();
        Resource equipment1 = new Equipment("Equipment1", LocalTime.of(9,0),LocalTime.of(20,0), Restriction.NonRestriction,"SerialNumber1");
        Resource equipment2 = new Equipment("Equipment1", LocalTime.of(9,0),LocalTime.of(20,0), Restriction.NonRestriction,"SerialNumber2");
        Resource equipment3 = new Equipment("Equipment2", null,null, Restriction.NonRestriction,"SerialNumber3");
        Resource indoorVenue1 = new IndoorVenue("Lab1",LocalTime.of(9,0),LocalTime.of(20,0),Restriction.ApprovalRequired,"Block 1","1R01");
        Resource indoorVenue2 = new IndoorVenue("Lab2",LocalTime.of(9,0),LocalTime.of(20,0),Restriction.ApprovalRequired,"Block 1","1R02");
        Resource indoorVenue3 = new IndoorVenue("Lab3",LocalTime.of(9,0),LocalTime.of(18,0),Restriction.ApprovalRequired,"Block 1","2R01");
        Resource outdoorVenue1 = new OutdoorVenue("Basketball Court",null,null,Restriction.NonRestriction,"Beside field");
        Resource outdoorVenue2 = new OutdoorVenue("Swimming Pool",null,null,Restriction.Restricted,"Beside main entrance (Construction on going)");
        resourceList.add(equipment1);
        resourceList.add(equipment2);
        resourceList.add(equipment3);
        resourceList.add(indoorVenue1);
        resourceList.add(indoorVenue2);
        resourceList.add(indoorVenue3);
        resourceList.add(outdoorVenue1);
        resourceList.add(outdoorVenue2);
        return resourceList;
    }
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
    public void testNewEvent(){
        for (Event event: testEventList()){
            administrativeStaffService.createNewEvent(event);
        }
    }
    private List<Event> testEventList(){
        List<Event> eventList = new ArrayList<>();
        List<User> lecturerList = administrativeStaffService.getUserByUserRole(UserRole.Lecturer);
        List<Venue> labList = venueService.filterVenue(null,"lab",null,null,null);
        Event event1 = new Event("Lab Open Event",
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().plusHours(15),
                "The event is open for anybody to visit the labs",
                labList,lecturerList);
        List<User> studentList = administrativeStaffService.getUserByUserRole(UserRole.Student);
        List<Venue> basketballCourt = venueService.filterVenue(null,"basketball",null,null,null);
        Event event2 = new Event("Basket ball event",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3),
                "The event is open for anybody to play ball",
                basketballCourt,studentList);
        eventList.add(event1);
        eventList.add(event2);
        return eventList;
    }
}
