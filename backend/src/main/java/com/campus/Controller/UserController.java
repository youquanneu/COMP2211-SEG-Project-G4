package com.campus.Controller;

import com.campus.Classification.Restriction;
import com.campus.Classification.UserRole;
import com.campus.DataTransferObject.Main.EmailDTO;
import com.campus.DataTransferObject.Main.OneTimePasswordDTO;
import com.campus.DataTransferObject.User.LoginRequest;
import com.campus.DataTransferObject.User.UserDTO;
import com.campus.Entity.Event.Event;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.*;
import com.campus.Entity.User.AdministrativeStaff;
import com.campus.Entity.User.Lecturer;
import com.campus.Entity.User.Student;
import com.campus.Entity.User.User;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserController implements CommandLineRunner{
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
                administrativeStaffService.addNewResource(resource);
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
    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    @Override
    public void run(String... args) throws Exception {
        testNewReservation();testNewEvent();
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
