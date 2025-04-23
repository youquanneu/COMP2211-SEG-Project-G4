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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/setup")
public class SetUpController  implements CommandLineRunner {
    private static final Logger logger = Logger.getLogger(SetUpController.class.getName());
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("——————————————————————————————————————————————————————————");
        logger.info("Application initialization starting...");
        
        try {
            // Check if database is already initialized
            if (!isDatabaseInitialized()) {
                logger.info("Running initial database setup...");
                
                logger.info("Step 1/6: Registering users");
                testRegisterUser();
                
                logger.info("Step 2/6: Adding resources");
                testAddNewResource();
                
                logger.info("Step 3/6: Generating time slots");
                generateAllTimeSlot();
                
                logger.info("Step 4/6: Creating reservations");
                testNewReservation();
                
                logger.info("Step 5/6: Creating events");
                testNewEvent();
                
                logger.info("Step 6/6: Adding emergency cases");
                addNewEmergencyCase();
                
                logger.info("Initial database setup completed successfully");
            } else {
                logger.info("Database already initialized, skipping setup");
            }
            
            // Verify critical components are initialized
            verifySetup();
        } catch (Exception e) {
            logger.severe("Error during initialization: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("——————————————————————————————————————————————————————————");
        logger.info("Application initialization complete");
    }
    
    /**
     * Checks if the database has already been initialized
     * @return true if database is already initialized, false otherwise
     */
    private boolean isDatabaseInitialized() {
        // Check if there are any users in the database
        List<User> existingUsers = administrativeStaffService.getUserByUserRole(UserRole.AdministrativeStaff);
        if (!existingUsers.isEmpty()) {
            return true;
        }
        
        // Check if there are any resources in the database
        List<Resource> existingResources = resourceService.getAllResource();
        return !existingResources.isEmpty();
    }

    public void testRegisterUser(){
        List<User> userForRegister = userListForRegisterTest();
        for (User user : userForRegister){
            administrativeStaffService.registerNewUser(user);
        }
    }
    private List<User> userListForRegisterTest(){
        List<User> userList = new ArrayList<>();
        
        // Students with realistic names and simple passwords
        User student1 = new Student("Abdullah", "aha1a22@soton.ac.uk", "passwordstd1");
        User student2 = new Student("Pang Gui Rou", "guirou23@soton.ac.uk", "passwordstd2");
        User student3 = new Student("John Doe", "gyx1e23@soton.ac.uk", "passwordstd3");
        User student4 = new Student("Abcdefg", "abc1e23@soton.ac.uk", "passwordstd4");
        User student5 = new Student("Shiqi Chen", "csq1e23@soton.ac.uk", "passwordstd5");
        
        // Lecturers with realistic names and simple passwords
        User lecturer1 = new Lecturer("Dr. Rajesh", "Rajesh@soton.ac.uk", "passwordlec1");
        User lecturer2 = new Lecturer("Dr. Najib", "Najib@soton.ac.uk", "passwordlec2");
        User lecturer3 = new Lecturer("Dr. Zila", "Zila@soton.ac.uk", "passwordlec3");
        
        // Administrative staff
        User administrativeStaff = new AdministrativeStaff("yqn", "yqn1e23@soton.ac.uk", "Password");
        
        // Add all users to the list
        userList.add(student1);
        userList.add(student2);
        userList.add(student3);
        userList.add(student4);
        userList.add(student5);
        userList.add(lecturer1);
        userList.add(lecturer2);
        userList.add(lecturer3);
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
        
        // Add equipment resources (keeping the original equipment for demo purposes)
        Resource equipment1 = new Equipment("Equipment1", LocalTime.of(9,0),LocalTime.of(20,0), Restriction.NonRestriction,"SerialNumber1");
        Resource equipment2 = new Equipment("Equipment2", LocalTime.of(9,0),LocalTime.of(20,0), Restriction.NonRestriction,"SerialNumber2");
        Resource equipment3 = new Equipment("Portable Projector", null,null, Restriction.NonRestriction,"SerialNumber3");
        resourceList.add(equipment1);
        resourceList.add(equipment2);
        resourceList.add(equipment3);
        
        // Add actual rooms from navigationData.js
        // Lecture Rooms
        resourceList.add(new IndoorVenue("3R002 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R002"));
        resourceList.add(new IndoorVenue("3R003 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R003"));
        resourceList.add(new IndoorVenue("3R004 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R004"));
        resourceList.add(new IndoorVenue("3R006 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R006"));
        resourceList.add(new IndoorVenue("3R009 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R009"));
        resourceList.add(new IndoorVenue("3R011 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R011"));
        resourceList.add(new IndoorVenue("3R017 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R017"));
        resourceList.add(new IndoorVenue("3R018 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R018"));
        resourceList.add(new IndoorVenue("3R019 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R019"));
        resourceList.add(new IndoorVenue("3R020 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R020"));
        resourceList.add(new IndoorVenue("3R021 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R021"));
        resourceList.add(new IndoorVenue("3R022 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R022"));
        resourceList.add(new IndoorVenue("3R024 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R024"));
        resourceList.add(new IndoorVenue("3R025 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R025"));
        resourceList.add(new IndoorVenue("3R030 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R030"));
        resourceList.add(new IndoorVenue("3R031 - Lecture Room", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R031"));
        
        // Labs
        resourceList.add(new IndoorVenue("3R010 - Mechanical Workshop", LocalTime.of(9,0),LocalTime.of(18,0), Restriction.ApprovalRequired, "Building 3", "3R010"));
        resourceList.add(new IndoorVenue("3R012 - Green Engineering Lab", LocalTime.of(9,0),LocalTime.of(18,0), Restriction.ApprovalRequired, "Building 3", "3R012"));
        resourceList.add(new IndoorVenue("3R013 - Materials and Structure Lab", LocalTime.of(9,0),LocalTime.of(18,0), Restriction.ApprovalRequired, "Building 3", "3R013"));
        resourceList.add(new IndoorVenue("3R014 - Aerospace Lab", LocalTime.of(9,0),LocalTime.of(18,0), Restriction.ApprovalRequired, "Building 3", "3R014"));
        resourceList.add(new IndoorVenue("3R016 - Thermodynamics & Fluid Mechanics Lab", LocalTime.of(9,0),LocalTime.of(18,0), Restriction.ApprovalRequired, "Building 3", "3R016"));
        resourceList.add(new IndoorVenue("3R023 - Computer Science Lab 1", LocalTime.of(9,0),LocalTime.of(20,0), Restriction.ApprovalRequired, "Building 3", "3R023"));
        resourceList.add(new IndoorVenue("3R027 - Computer Science Lab 2", LocalTime.of(9,0),LocalTime.of(20,0), Restriction.ApprovalRequired, "Building 3", "3R027"));
        resourceList.add(new IndoorVenue("3R028 - Computer Science Lab 3", LocalTime.of(9,0),LocalTime.of(20,0), Restriction.ApprovalRequired, "Building 3", "3R028"));
        resourceList.add(new IndoorVenue("3R032 - Engineering Foundation Lab 1", LocalTime.of(9,0),LocalTime.of(18,0), Restriction.ApprovalRequired, "Building 3", "3R032"));
        resourceList.add(new IndoorVenue("3R033 - Engineering Foundation Lab 2", LocalTime.of(9,0),LocalTime.of(18,0), Restriction.ApprovalRequired, "Building 3", "3R033"));
        resourceList.add(new IndoorVenue("3R034 - Engineering Foundation Lab 3", LocalTime.of(9,0),LocalTime.of(18,0), Restriction.ApprovalRequired, "Building 3", "3R034"));
        
        // Special rooms
        resourceList.add(new IndoorVenue("3R005 - Enterprise and Innovation Centre", LocalTime.of(9,0),LocalTime.of(18,0), Restriction.ApprovalRequired, "Building 3", "3R005"));
        resourceList.add(new IndoorVenue("3R008 - RMC", LocalTime.of(9,0),LocalTime.of(18,0), Restriction.ApprovalRequired, "Building 3", "3R008"));
        resourceList.add(new IndoorVenue("3R015 - Design Studio", LocalTime.of(9,0),LocalTime.of(20,0), Restriction.ApprovalRequired, "Building 3", "3R015"));
        resourceList.add(new IndoorVenue("3R026 - Lecture Hall", LocalTime.of(8,0),LocalTime.of(22,0), Restriction.ApprovalRequired, "Building 3", "3R026"));
        resourceList.add(new IndoorVenue("3R035 - Female Surau", LocalTime.of(6,0),LocalTime.of(22,0), Restriction.NonRestriction, "Building 3", "3R035"));
        
        // Bathrooms
        resourceList.add(new IndoorVenue("3R011T - Women's Bathroom", LocalTime.of(0,0),LocalTime.of(23,59), Restriction.NonRestriction, "Building 3", "3R011T"));
        resourceList.add(new IndoorVenue("3R012T - Men's Bathroom", LocalTime.of(0,0),LocalTime.of(23,59), Restriction.NonRestriction, "Building 3", "3R012T"));
        
        // Add an outdoor venue
        Resource outdoorVenue1 = new OutdoorVenue("Basketball Court", LocalTime.of(6,0),LocalTime.of(22,0), Restriction.NonRestriction, "Beside field");
        resourceList.add(outdoorVenue1);
        
        return resourceList;
    }
    public void testNewReservation(){
        for (Reservation reservation : reservationsTestList()){
            reservationService.saveReservation(reservation);
        }
    }
    private List<Reservation> reservationsTestList(){
        List<Reservation> reservations = new ArrayList<>();
        
        // Get users by name for more realistic reservations
        List<User> allUsers = administrativeStaffService.getAllUsers();
        User abdullah = null;
        User rajesh = null;
        
        // Find specific users by name
        for (User user : allUsers) {
            if (user.getUsername().contains("Abdullah")) {
                abdullah = user;
            } else if (user.getUsername().contains("Rajesh")) {
                rajesh = user;
            }
            
            // Break if we found both users
            if (abdullah != null && rajesh != null) {
                break;
            }
        }
        
        // Fallback if users not found
        if (abdullah == null) {
            List<User> students = administrativeStaffService.getUserByUserRole(UserRole.Student);
            if (!students.isEmpty()) {
                abdullah = students.get(0);
            }
        }
        
        if (rajesh == null) {
            List<User> lecturers = administrativeStaffService.getUserByUserRole(UserRole.Lecturer);
            if (!lecturers.isEmpty()) {
                rajesh = lecturers.get(0);
            }
        }
        
        // Find actual resources to reserve
        List<Resource> allResources = resourceService.getAllResource();
        
        // Find specific resources for reservations
        Resource lectureRoom = null;
        Resource lab = null;
        Resource equipment = null;
        
        for (Resource resource : allResources) {
            if (resource instanceof Equipment) {
                equipment = resource;
            } else if (resource instanceof IndoorVenue) {
                String resourceName = resource.getResourceName();
                if (resourceName.contains("3R012 - Green Engineering Lab")) {
                    lab = resource;
                } else if (resourceName.contains("3R002 - Lecture Room")) {
                    lectureRoom = resource;
                }
            }
            
            // Break if we've found specific resources
            if (equipment != null && lab != null && lectureRoom != null) {
                break;
            }
        }
        
        // Create future dates for sample reservations
        LocalDateTime nextMonday = LocalDateTime.now().plusDays(7).withHour(10).withMinute(0);
        LocalDateTime nextTuesday = LocalDateTime.now().plusDays(8).withHour(14).withMinute(0);
        LocalDateTime nextWednesday = LocalDateTime.now().plusDays(9).withHour(9).withMinute(0);
        LocalDateTime nextThursday = LocalDateTime.now().plusDays(10).withHour(15).withMinute(0);
        LocalDateTime nextFriday = LocalDateTime.now().plusDays(11).withHour(13).withMinute(0);
        
        // Create reservations with found resources and users
        if (equipment != null && abdullah != null) {
            Reservation reservation1 = reservationService.createNewReservation(
                    abdullah,
                    equipment,
                    Purpose.Study,
                    nextMonday,
                    nextMonday.plusHours(2));
            
            if (reservation1 != null) {
                reservations.add(reservation1);
                logger.info("Created reservation: Equipment for Study");
            }
        }
        
        if (equipment != null && rajesh != null) {
            Reservation reservation4 = reservationService.createNewReservation(
                    rajesh,
                    equipment,
                    Purpose.Presentation,
                    nextTuesday,
                    nextTuesday.plusHours(2));
            
            if (reservation4 != null) {
                reservations.add(reservation4);
                logger.info("Created reservation: Equipment for Presentation");
            }
        }
        
        if (lab != null && abdullah != null) {
            Reservation reservation3 = reservationService.createNewReservation(
                    abdullah,
                    lab,
                    Purpose.Workshop,
                    nextWednesday,
                    nextWednesday.plusHours(2));
            
            if (reservation3 != null) {
                reservations.add(reservation3);
                logger.info("Created reservation: Lab for Workshop");
            }
        }
        
        if (lectureRoom != null && rajesh != null) {
            Reservation reservation5 = reservationService.createNewReservation(
                    rajesh,
                    lectureRoom,
                    Purpose.Meeting,
                    nextThursday,
                    nextThursday.plusHours(2));
            
            if (reservation5 != null) {
                reservations.add(reservation5);
                logger.info("Created reservation: Lecture Room for Meeting");
            }
        }
        
        if (lectureRoom != null && abdullah != null) {
            Reservation reservation2 = reservationService.createNewReservation(
                    abdullah,
                    lectureRoom,
                    Purpose.Study,
                    nextFriday,
                    nextFriday.plusHours(2));
            
            if (reservation2 != null) {
                reservations.add(reservation2);
                logger.info("Created reservation: Lecture Room for Study");
            }
        }
        
        logger.info("Generated " + reservations.size() + " test reservations");
        return reservations;
    }
    public void testNewEvent(){
        for (Event event: testEventList()){
            administrativeStaffService.createNewEvent(event);
        }
    }
    private List<Event> testEventList(){
        List<Event> eventList = new ArrayList<>();
        
        // Get users by name for more targeted organizer selection
        List<User> allUsers = administrativeStaffService.getAllUsers();
        User rajesh = null;
        User najib = null;
        User zila = null;
        
        // Find specific lecturers by name
        for (User user : allUsers) {
            if (user.getUsername().contains("Rajesh")) {
                rajesh = user;
            } else if (user.getUsername().contains("Najib")) {
                najib = user;
            } else if (user.getUsername().contains("Zila")) {
                zila = user;
            }
        }
        
        // Fallback to first lecturer if specific ones not found
        List<User> lecturers = administrativeStaffService.getUserByUserRole(UserRole.Lecturer);
        if (rajesh == null && !lecturers.isEmpty()) rajesh = lecturers.get(0);
        if (najib == null && lecturers.size() > 1) najib = lecturers.get(1);
        if (zila == null && lecturers.size() > 2) zila = lecturers.get(2);
        
        // Get venue types for specific events
        List<Venue> allVenues = venueService.getAllVenue();
        
        // Find specific venues for the events
        Venue greenEngineeringLab = null;
        Venue computerScienceLab = null;
        Venue lectureHall = null;
        Venue basketballCourt = null;
        
        for (Venue venue : allVenues) {
            String venueName = venue.getResourceName();
            if (venueName.contains("3R012 - Green Engineering Lab")) {
                greenEngineeringLab = venue;
            } else if (venueName.contains("3R023 - Computer Science Lab 1")) {
                computerScienceLab = venue;
            } else if (venueName.contains("3R026 - Lecture Hall")) {
                lectureHall = venue;
            } else if (venueName.contains("Basketball Court")) {
                basketballCourt = venue;
            }
        }
        
        // Build future dates for events to ensure they're visible
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
        LocalDateTime nextWeek = LocalDateTime.now().plusDays(7).withHour(14).withMinute(0);
        LocalDateTime twoWeeksFromNow = LocalDateTime.now().plusDays(14).withHour(9).withMinute(0);
        LocalDateTime threeWeeksFromNow = LocalDateTime.now().plusDays(21).withHour(15).withMinute(0);
        
        // Event 1: Workshop in Green Engineering Lab organized by Dr. Rajesh
        if (greenEngineeringLab != null && rajesh != null) {
            List<Venue> singleVenue = new ArrayList<>();
            singleVenue.add(greenEngineeringLab);
            
            List<User> singleOrganizer = new ArrayList<>();
            singleOrganizer.add(rajesh);
            
            Event sustainabilityWorkshop = new Event(
                    "Sustainable Engineering Workshop",
                    "Workshop",
                    tomorrow,
                    tomorrow.plusHours(6),
                    "A hands-on workshop exploring sustainable engineering practices and green technologies.",
                    singleVenue, singleOrganizer);
            
            eventList.add(sustainabilityWorkshop);
            logger.info("Created event: Sustainable Engineering Workshop");
        }
        
        // Event 2: Guest lecture in Computer Science Lab organized by Dr. Najib
        if (computerScienceLab != null && najib != null) {
            List<Venue> singleVenue = new ArrayList<>();
            singleVenue.add(computerScienceLab);
            
            List<User> singleOrganizer = new ArrayList<>();
            singleOrganizer.add(najib);
            
            Event aiLecture = new Event(
                    "Artificial Intelligence: Current Trends",
                    "Presentation",
                    nextWeek,
                    nextWeek.plusHours(2),
                    "A guest lecture on the latest developments in artificial intelligence and machine learning.",
                    singleVenue, singleOrganizer);
            
            eventList.add(aiLecture);
            logger.info("Created event: AI Current Trends");
        }
        
        // Event 3: Department meeting in Lecture Hall organized by Dr. Zila
        if (lectureHall != null && zila != null) {
            List<Venue> singleVenue = new ArrayList<>();
            singleVenue.add(lectureHall);
            
            List<User> singleOrganizer = new ArrayList<>();
            singleOrganizer.add(zila);
            
            Event departmentMeeting = new Event(
                    "Engineering Faculty Meeting",
                    "Meeting",
                    twoWeeksFromNow,
                    twoWeeksFromNow.plusHours(2),
                    "Quarterly department meeting to discuss curriculum updates and research priorities.",
                    singleVenue, singleOrganizer);
            
            eventList.add(departmentMeeting);
            logger.info("Created event: Engineering Faculty Meeting");
        }
        
        // Event 4: Basketball tournament on the basketball court
        if (basketballCourt != null && rajesh != null) {
            List<Venue> singleVenue = new ArrayList<>();
            singleVenue.add(basketballCourt);
            
            List<User> singleOrganizer = new ArrayList<>();
            singleOrganizer.add(rajesh);
            
            Event basketballTournament = new Event(
                    "Faculty vs. Students Basketball Tournament",
                    "Sports",
                    threeWeeksFromNow,
                    threeWeeksFromNow.plusHours(3),
                    "Annual basketball tournament between faculty members and students. All are welcome to participate or spectate.",
                    singleVenue, singleOrganizer);
            
            eventList.add(basketballTournament);
            logger.info("Created event: Basketball Tournament");
        }
        
        logger.info("Generated " + eventList.size() + " events");
        return eventList;
    }
    private void generateAllTimeSlot(){
        for (TimeSlot timeSlot : initialTimeSlot()){
            timeSlotService.saveTimeSlot(timeSlot);
        }
    }
    private List<TimeSlot> initialTimeSlot(){
        List<TimeSlot> timeSlots = new ArrayList<>();
        
        // Create time slots for normal working hours (8 AM to 10 PM)
        // This avoids creating overnight slots that may cause issues
        for (int hour = 8; hour < 22; hour++) {
            // Create a full hour slot (e.g., 8:00-9:00)
            LocalTime start = LocalTime.of(hour, 0);
            LocalTime end = LocalTime.of(hour + 1, 0);
            timeSlots.add(new TimeSlot(start, end));
            
            // Create half-hour slots for more booking flexibility
            LocalTime halfStart = LocalTime.of(hour, 0);
            LocalTime halfEnd = LocalTime.of(hour, 30);
            timeSlots.add(new TimeSlot(halfStart, halfEnd));
            
            LocalTime halfStart2 = LocalTime.of(hour, 30);
            LocalTime halfEnd2 = hour == 21 ? LocalTime.of(22, 0) : LocalTime.of(hour + 1, 0);
            timeSlots.add(new TimeSlot(halfStart2, halfEnd2));
        }
        
        logger.info("Generated " + timeSlots.size() + " time slots for booking");
        return timeSlots;
    }
    private void addNewEmergencyCase(){
        for (EmergencyCase emergencyCase : emergencyCaseList()){
            emergencyCaseService.reportNewCase(emergencyCase);
        }
    }
    private List<EmergencyCase> emergencyCaseList(){
        List<EmergencyCase> emergencyCaseList = new ArrayList<>();
        
        // Get actual venues from the database instead of hardcoded IDs
        List<Venue> allVenues = venueService.getAllVenue();
        
        // Find specific venues by name
        Venue lab1 = null;
        Venue lab2 = null; 
        Venue lectureHall = null;
        
        for (Venue venue : allVenues) {
            if (venue.getResourceName().contains("3R012 - Green Engineering Lab")) {
                lab1 = venue;
            } else if (venue.getResourceName().contains("3R023 - Computer Science Lab 1")) {
                lab2 = venue;
            } else if (venue.getResourceName().contains("3R026 - Lecture Hall")) {
                lectureHall = venue;
            }
            
            // Break early if we found all venues
            if (lab1 != null && lab2 != null && lectureHall != null) {
                break;
            }
        }
        
        // Fall back to first venues if we couldn't find the specific ones
        if (lab1 == null && !allVenues.isEmpty()) lab1 = allVenues.get(0);
        if (lab2 == null && allVenues.size() > 1) lab2 = allVenues.get(1);
        if (lectureHall == null && allVenues.size() > 2) lectureHall = allVenues.get(2);
        
        // Add emergency cases with the actual venues and updated user emails
        if (lab1 != null) {
            emergencyCaseList.add(new EmergencyCase(lab1, "aha1a22@soton.ac.uk", "Fire alarm triggered", "Smoke detected in lab area"));
        }
        
        if (lab2 != null) {
            emergencyCaseList.add(new EmergencyCase(lab2, "Rajesh@soton.ac.uk", "Health emergency", "Student feeling unwell during lab session"));
        }
        
        if (lectureHall != null) {
            emergencyCaseList.add(new EmergencyCase(lectureHall, "visitor@soton.ac.uk", "Plumbing issue", "Water leaking from ceiling"));
        }
        
        return emergencyCaseList;
    }
    /**
     * Verifies that critical components are initialized
     */
    private void verifySetup() {
        // Check time slots
        List<TimeSlot> timeSlots = timeSlotService.getTimeSlot();
        logger.info("Found " + timeSlots.size() + " time slots in database");
        if (timeSlots.isEmpty()) {
            logger.warning("No time slots found! Generating them now...");
            generateAllTimeSlot();
        }
        
        // Check resources
        List<Resource> resources = resourceService.getAllResource();
        logger.info("Found " + resources.size() + " resources in database");
        
        // Check events - use eventService for this
        // We don't have direct access to it, so just log what we know
        logger.info("Verifying events...");
        
        // Check users
        List<User> users = administrativeStaffService.getAllUsers();
        logger.info("Found " + users.size() + " users in database");
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
