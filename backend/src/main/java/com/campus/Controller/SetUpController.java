package com.campus.Controller;

import com.campus.Classification.Purpose;
import com.campus.Classification.Restriction;
import com.campus.Classification.UserRole;
import com.campus.Entity.Event.EmergencyCase;
import com.campus.Entity.Event.Event;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Reservation.TimeSlot;
import com.campus.Entity.Resource.*;
import com.campus.Entity.User.AdministrativeStaff;
import com.campus.Entity.User.Lecturer;
import com.campus.Entity.User.Student;
import com.campus.Entity.User.User;
import com.campus.Repository.Reservation.TimeSlotRepository;
import com.campus.Service.Event.EmergencyCaseService;
import com.campus.Service.Reservation.ReservationService;
import com.campus.Service.Reservation.TimeSlotService;
import com.campus.Service.Resource.ResourceService;
import com.campus.Service.Resource.VenueService;
import com.campus.Service.User.AdministrativeStaffService;
import com.campus.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@RestController
@RequestMapping("/setup")
public class SetUpController  implements CommandLineRunner {
    private static final Logger logger = Logger.getLogger(SetUpController.class.getName());
    @Override
    public void run(String... args) throws Exception {
        System.out.println("——————————————————————————————————————————————————————————");
        logger.info("Application start here");
//        testRegisterUser();
//        testAddNewResource();
//        testNewReservation();
//        testNewEvent();
//        generateAllTimeSlot();
//        addNewEmergencyCase();
        System.out.println("——————————————————————————————————————————————————————————");
        logger.info("Initial Set Up complete");
    }

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
        User administrativeStaff = new AdministrativeStaff("yqn","yqn1e23@soton.ac.uk","Password");
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
                Purpose.Meeting,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1));
        Reservation reservation2 = reservationService.createNewReservation(
                userService.getUserById(1),
                resourceService.getResourceByID(6),
                Purpose.Meeting,
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusWeeks(1));
        Reservation reservation3 = reservationService.createNewReservation(
                userService.getUserById(1),
                resourceService.getResourceByID(3),
                Purpose.Meeting,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1));
        Reservation reservation4 = reservationService.createNewReservation(
                userService.getUserById(4),
                resourceService.getResourceByID(2),
                Purpose.Meeting,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1));
        Reservation reservation5 = reservationService.createNewReservation(
                userService.getUserById(4),
                resourceService.getResourceByID(2),
                Purpose.Meeting,
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
                "Fun",
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().plusHours(15),
                "The event is open for anybody to visit the labs",
                labList,lecturerList);
        List<User> studentList = administrativeStaffService.getUserByUserRole(UserRole.Student);
        List<Venue> basketballCourt = venueService.filterVenue(null,"basketball",null,null,null);
        Event event2 = new Event("Basket ball event",
                "Event",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3),
                "The event is open for anybody to play ball",
                basketballCourt,studentList);
        eventList.add(event1);
        eventList.add(event2);
        return eventList;
    }
    private void generateAllTimeSlot(){
        for (TimeSlot timeSlot : initialTimeSlot()){
            timeSlotService.saveTimeSlot(timeSlot);
        }
    }
    private List<TimeSlot> initialTimeSlot(){
        List<TimeSlot> timeSlots = new ArrayList<>();
        for (int hour = 0; hour < 24; hour++) {
            LocalTime start = LocalTime.of(hour, 0);
            LocalTime end = LocalTime.of((hour + 1) % 24, 0);
            TimeSlot timeSlot = new TimeSlot(start, end);
            timeSlots.add(timeSlot);
        }
        return timeSlots;
    }
    private void addNewEmergencyCase(){
        for (EmergencyCase emergencyCase : emergencyCaseList()){
            emergencyCaseService.reportNewCase(emergencyCase);
        }
    }
    private List<EmergencyCase> emergencyCaseList(){
        List<EmergencyCase> emergencyCaseList = new ArrayList<>();
        Venue venue1 = (Venue) venueService.getResourceByID(4);
        Venue venue2 = (Venue) venueService.getResourceByID(5);
        Venue venue3 = (Venue) venueService.getResourceByID(6);
        emergencyCaseList.add(new EmergencyCase(venue1,"user1@gmail.com","New Fire case",""));
        emergencyCaseList.add(new EmergencyCase(venue2,"user2@gmail.com","Health emergency case",""));
        emergencyCaseList.add(new EmergencyCase(venue3,"visitor","Unknown emergency case",""));
        return emergencyCaseList;
    }
    @Autowired
    private AdministrativeStaffService administrativeStaffService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private VenueService venueService;
    @Autowired
    private TimeSlotService timeSlotService;
    @Autowired
    private EmergencyCaseService emergencyCaseService;
}
