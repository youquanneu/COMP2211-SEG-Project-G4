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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/setup")
public class SetUpController  implements CommandLineRunner {
    private static final Logger logger = Logger.getLogger(SetUpController.class.getName());
    @Override
    public void run(String... args) throws Exception {
        System.out.println("——————————————————————————————————————————————————————————");
        logger.info("Application start here");
        initializeUser();
        initializeResource();
        initializeReservation();
        initializeTimeSlot();
        initializeEmergencyCase();
        testNewEvent();
        System.out.println("——————————————————————————————————————————————————————————");
        logger.info("Initial Set Up complete");
    }

    private void initializeUser(){
        for (User user : buildInitializeUserList()){
            administrativeStaffService.registerNewUser(user);
        }
    }
    private List<User> buildInitializeUserList(){
        List<User> userList = new ArrayList<>();

        // Students with realistic names and simple passwords
        userList.add(new Student("Abdullah", "aha1a22@soton.ac.uk", "passwordStd1"));
        userList.add(new Student("Pang Gui Rou", "guirou23@soton.ac.uk", "passwordStd2"));
        userList.add(new Student("John Doe", "gyx1e23@soton.ac.uk", "passwordStd3"));
        userList.add(new Student("ShiQi Chen", "csq1e23@soton.ac.uk", "passwordStd4"));

        // Lecturers with realistic names and simple passwords
        userList.add(new Lecturer("Dr. Rajesh", "blockCode.rajesh.yadav@southampton.ac.uk", "passwordLec1"));
        userList.add(new Lecturer("Dr. Najib", "blockCode.m.n.zamri@soton.ac.uk", "passwordLec2"));
        userList.add(new Lecturer("Dr. Zila", "blockCode.r.ramli@soton.ac.uk", "passwordLec3"));

        // Others king of user with realistic names and simple passwords
        userList.add(new User("Computer Science Student Club","blockCode.cssc@soton.ac.uk","passwordCSSC",UserRole.Organization));

        // Administrative staff
        userList.add(new AdministrativeStaff("You Quan", "yqn1e23@soton.ac.uk", "Password"));

        return userList;
    }

    private void initializeResource(){
        for (Resource resource : buildInitializeResourceList()){
            administrativeStaffService.addNewResource(resource);
        }
    }
    private List<Resource> buildInitializeResourceList(){
        List<Resource> resourceList = new ArrayList<>();

        LocalTime   campusOpen  = LocalTime.of(7,0);
        LocalTime   campusClose = LocalTime.of(22,0);
        LocalTime   labOpen     = LocalTime.of(9,0);
        LocalTime   labClose    = LocalTime.of(18,0);
        String      building    = "Level 3 Right Wing";

        // Add actual rooms from navigationData.js
        // Lecture Rooms
        resourceList.add(new IndoorVenue("3R002 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R002"));
        resourceList.add(new IndoorVenue("3R003 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R003"));
        resourceList.add(new IndoorVenue("3R004 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R004"));
        resourceList.add(new IndoorVenue("3R006 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R006"));
        resourceList.add(new IndoorVenue("3R009 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R009"));
        resourceList.add(new IndoorVenue("3R011 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R011"));
        resourceList.add(new IndoorVenue("3R017 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R017"));
        resourceList.add(new IndoorVenue("3R018 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R018"));
        resourceList.add(new IndoorVenue("3R019 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R019"));
        resourceList.add(new IndoorVenue("3R020 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R020"));
        resourceList.add(new IndoorVenue("3R021 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R021"));
        resourceList.add(new IndoorVenue("3R022 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R022"));
        resourceList.add(new IndoorVenue("3R024 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R024"));
        resourceList.add(new IndoorVenue("3R025 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R025"));
        resourceList.add(new IndoorVenue("3R030 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R030"));
        resourceList.add(new IndoorVenue("3R031 - Lecture Room", campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R031"));

        // Labs
        resourceList.add(new IndoorVenue("3R010 - Mechanical Workshop",             labOpen,labClose, Restriction.ApprovalRequired, building, "3R010"));
        resourceList.add(new IndoorVenue("3R012 - Green Engineering Lab",           labOpen,labClose, Restriction.ApprovalRequired, building, "3R012"));
        resourceList.add(new IndoorVenue("3R013 - Materials and Structure Lab",     labOpen,labClose, Restriction.ApprovalRequired, building, "3R013"));
        resourceList.add(new IndoorVenue("3R014 - Aerospace Lab",                   labOpen,labClose, Restriction.ApprovalRequired, building, "3R014"));
        resourceList.add(new IndoorVenue("3R016 - Thermodynamics & Fluid Mechanics Lab",    labOpen,labClose, Restriction.ApprovalRequired, building, "3R016"));
        resourceList.add(new IndoorVenue("3R023 - Computer Science Lab 1",          labOpen,campusClose, Restriction.ApprovalRequired, building, "3R023"));
        resourceList.add(new IndoorVenue("3R027 - Computer Science Lab 2",          labOpen,campusClose, Restriction.ApprovalRequired, building, "3R027"));
        resourceList.add(new IndoorVenue("3R028 - Computer Science Lab 3",          labOpen,campusClose, Restriction.ApprovalRequired, building, "3R028"));
        resourceList.add(new IndoorVenue("3R032 - Engineering Foundation Lab 1",    labOpen,labClose, Restriction.ApprovalRequired, building, "3R032"));
        resourceList.add(new IndoorVenue("3R033 - Engineering Foundation Lab 2",    labOpen,labClose, Restriction.ApprovalRequired, building, "3R033"));
        resourceList.add(new IndoorVenue("3R034 - Engineering Foundation Lab 3",    labOpen,labClose, Restriction.ApprovalRequired, building, "3R034"));

        // Special rooms
        resourceList.add(new IndoorVenue("3R005 - Enterprise and Innovation Centre", labOpen,labClose, Restriction.ApprovalRequired, building, "3R005"));
        resourceList.add(new IndoorVenue("3R008 - RMC",             labOpen,labClose,       Restriction.ApprovalRequired, building, "3R008"));
        resourceList.add(new IndoorVenue("3R015 - Design Studio",   campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R015"));
        resourceList.add(new IndoorVenue("3R026 - Lecture Hall",    campusOpen,campusClose, Restriction.ApprovalRequired, building, "3R026"));
        resourceList.add(new IndoorVenue("3R035 - Female Surau",    campusOpen,campusClose, Restriction.NonBookable, building, "3R035"));

        // Bathrooms
        resourceList.add(new IndoorVenue("3R011T - Women's Bathroom",null,null, Restriction.NonBookable, building, "3R011T"));
        resourceList.add(new IndoorVenue("3R012T - Men's Bathroom",  null,null, Restriction.NonBookable, building, "3R012T"));

        // Equipment
        resourceList.add(new Equipment("Foosball Table",campusOpen,campusClose, Restriction.NonRestriction,null));
        resourceList.add(new Equipment("Table Tennis",  campusOpen,campusClose, Restriction.NonRestriction,null));
        resourceList.add(new Equipment("Portable Projector", null,null, Restriction.NonRestriction,"PP1009433"));

        //
        resourceList.add(new OutdoorVenue("Car Park",null,null,Restriction.NonBookable,null));
        return resourceList;
    }
    private void initializeReservation(){
        for (Reservation reservation : buildInitializeReservationList()){
            reservationService.saveReservation(reservation);
        }
    }
    private List<Reservation> buildInitializeReservationList(){
        List<Reservation> reservations =new ArrayList<>();
        reservations.add( reservationService.createNewReservation(
                userService.getUserById(1),
                resourceService.getResourceByID(1),
                Purpose.Study,
                LocalDateTime.now().plusDays(1).withHour(8),
                LocalDateTime.now().plusDays(1).withHour(10))
        );
        reservations.add( reservationService.createNewReservation(
                userService.getUserById(2),
                resourceService.getResourceByID(2),
                Purpose.Meeting,
                LocalDateTime.now().plusDays(1).withHour(12),
                LocalDateTime.now().plusDays(1).withHour(13))
        );
        reservations.add( reservationService.createNewReservation(
                userService.getUserById(3),
                resourceService.getResourceByID(3),
                Purpose.Presentation,
                LocalDateTime.now().plusDays(1).withHour(10),
                LocalDateTime.now().plusDays(1).withHour(11))
        );
        reservations.add( reservationService.createNewReservation(
                userService.getUserById(4),
                resourceService.getResourceByID(4),
                Purpose.Workshop,
                LocalDateTime.now().plusDays(1).withHour(10),
                LocalDateTime.now().plusDays(1).withHour(11))
        );
        reservations.add( reservationService.createNewReservation(
                userService.getUserById(1),
                resourceService.getResourceByID(5),
                Purpose.Meeting,
                LocalDateTime.now().plusDays(1).withHour(10),
                LocalDateTime.now().plusDays(1).withHour(11))
        );
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
        List<User> studentList = administrativeStaffService.getUserByUserRole(UserRole.Student);
        List<Venue> labList = venueService.filterVenue(null,"lab",null,null,null);
        List<Venue> basketballCourt = venueService.filterVenue(null,"basketball",null,null,null);
        Event event1 = new Event("Lab Open Event",
                "Open for visiting",
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().plusHours(15),
                "The event is open for anybody to visit the labs",
                labList,lecturerList);

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
    private void initializeTimeSlot(){
        for (TimeSlot timeSlot : buildInitializeTimeSlotList()){
            timeSlotService.saveTimeSlot(timeSlot);
        }
    }
    private List<TimeSlot> buildInitializeTimeSlotList(){
        List<TimeSlot> timeSlots = new ArrayList<>();
        for (int hour = 7; hour < 22; hour++) {
            LocalTime start = LocalTime.of(hour, 0);
            LocalTime end = LocalTime.of((hour + 1) % 24, 0);
            TimeSlot timeSlot = new TimeSlot(start, end);
            timeSlots.add(timeSlot);
        }
        return timeSlots;
    }
    private void initializeEmergencyCase(){
        for (EmergencyCase emergencyCase : buildInitializeEmergencyCaseList()){
            emergencyCaseService.reportNewCase(emergencyCase);
        }
    }
    private List<EmergencyCase> buildInitializeEmergencyCaseList(){
        List<EmergencyCase> emergencyCaseList = new ArrayList<>();
        Venue venue1 = (Venue) venueService.getResourceByID(20);
        Venue venue2 = (Venue) venueService.getResourceByID(5);
        emergencyCaseList.add(new EmergencyCase(venue1,"Fire alarm triggered","Smoke detected in lab area","aha1a22@gmail.com"));
        emergencyCaseList.add(new EmergencyCase(venue2,"Health emergency","Health emergency case","Rajesh@soton.ac.uk"));
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
