package com.campus.Service.User;

import com.campus.Classification.Restriction;
import com.campus.Classification.UserRole;
import com.campus.Entity.Event.Event;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.*;
import com.campus.Entity.User.AdministrativeStaff;
import com.campus.Entity.User.Lecturer;
import com.campus.Entity.User.Student;
import com.campus.Entity.User.User;
import com.campus.Service.Resource.ResourceService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdministrativeStaffServiceTest {
    private AdministrativeStaffService administrativeStaffService;
    private UserService userService;
    private ResourceService resourceService;
    @Test
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
        User student3 = new Student("student2","student3@gmail.com","passwordStd3");
        User lecturer1 = new Lecturer("lecturer1","lecturer1@gmail.com","passwordLec1");
        User lecturer2 = new Lecturer("lecturer2","lecturer1@gmail.com","passwordLec2");
        User administrativeStaff = new AdministrativeStaff("Admin1","Admin1@gmail.com","adminPassword");
        userList.add(student1);
        userList.add(student2);
        userList.add(student3);
        userList.add(lecturer1);
        userList.add(lecturer2);
        userList.add(administrativeStaff);
        return userList;
    }
    @org.junit.Test
    public void testModifyUserInformation(){
        User user = userService.getUserById(1);
        administrativeStaffService.modifyUsername(user,"Alex");
        administrativeStaffService.modifyEmail(user,"Alex@gmail.com");
    }
    @org.junit.Test
    public void testDeleteUser(){
        User user = userService.getUserById(3);
        administrativeStaffService.deleteUser(user);
    }
    @org.junit.Test
    public void testGetByRole(){
        System.out.println("Student: ");
        System.out.println(administrativeStaffService.getUserByUserRole(UserRole.Student));
        System.out.println();
        System.out.println("Lecturer: ");
        System.out.println(administrativeStaffService.getUserByUserRole(UserRole.Lecturer));
        System.out.println();
        System.out.println("Administrative Staff: ");
        System.out.println(administrativeStaffService.getUserByUserRole(UserRole.AdministrativeStaff));
        System.out.println();
    }
    @org.junit.Test
    public void testFilterUsers(){
        System.out.println("No filtering : ");
        System.out.println(administrativeStaffService.getAllUsers());
        System.out.println();
        System.out.println("Find by user Id : ");
        System.out.println(administrativeStaffService.filterUsers(3,
                null,null,null));
        System.out.println();
        System.out.println("Filter by user username : ");
        System.out.println(administrativeStaffService.filterUsers(null,
                "de",null,null));
        System.out.println();
        System.out.println("Filter by user email : ");
        System.out.println(administrativeStaffService.filterUsers(null,
                null,"tu",null));
        System.out.println();
        System.out.println("Filter by user user role and username: ");
        System.out.println(administrativeStaffService.filterUsers(null,
                "1",null, UserRole.Student));
        System.out.println();
    }
    @org.junit.Test
    public void testAddNewResource(){
        for (Resource resource: testResourceList()) {
            administrativeStaffService.addNewResource(resource  );
        }
    }   // Demonstration method : Add a new resource
    private List<Resource> testResourceList(){
        List<Resource> resourceList = new ArrayList<>();
        Resource equipment1 = new Equipment("Equipment1", LocalTime.of(9,0),LocalTime.of(20,0), Restriction.NonRestriction,"SerialNumber1");
        Resource equipment2 = new Equipment("Equipment1", LocalTime.of(9,0),LocalTime.of(20,0), Restriction.NonRestriction,"SerialNumber2");
        Resource equipment3 = new Equipment("Equipment1", LocalTime.of(9,0),LocalTime.of(20,0), Restriction.NonRestriction,"SerialNumber1");
        Resource indoorVenue1 = new IndoorVenue("Lab1",LocalTime.of(9,0),LocalTime.of(20,0),Restriction.ApprovalRequired,"Block 2","3R01");
        Resource indoorVenue2 = new IndoorVenue("Lab2",LocalTime.of(9,0),LocalTime.of(20,0),Restriction.ApprovalRequired,"Block 2","3R01");
        Resource indoorVenue3 = new IndoorVenue("Lab3",LocalTime.of(9,0),LocalTime.of(18,0),Restriction.ApprovalRequired,"Block 3","3R01");
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
    @org.junit.Test
    public void testChangeInfo(){
        Resource resource = resourceService.getResourceByID(1);
        administrativeStaffService.changeResourceName(resource,"Computer");
        administrativeStaffService.changeOpenTime(resource,null);
        administrativeStaffService.changeCloseTime(resource,null);
        administrativeStaffService.changeRestriction(resource,Restriction.ApprovalRequired);
    }
    @org.junit.Test
    public void testDeleteResource(){
        administrativeStaffService.deleteResource(resourceService.getResourceByID(4));
    }
//    class Unknown{
//        public void testRegisterUser(){
//            List<User> userForRegister = userListForRegisterTest();
//            for (User user : userForRegister){
//                administrativeStaffService.registerNewUser(user);
//            }
//        }
//        private List<User> userListForRegisterTest(){
//            List<User> userList = new ArrayList<>();
//            User student1 = new Student("student1","student1@gmail.com","passwordStd1");
//            User student2 = new Student("student2","student2@gmail.com","passwordStd2");
//            User student3 = new Student("student3","student3@gmail.com","passwordStd3");
//            User lecturer1 = new Lecturer("lecturer1","lecturer1@gmail.com","passwordLec1");
//            User lecturer2 = new Lecturer("lecturer2","lecturer2@gmail.com","passwordLec2");
//            User administrativeStaff = new AdministrativeStaff("Admin1","Admin1@gmail.com","adminPassword");
//            userList.add(student1);
//            userList.add(student2);
//            userList.add(student3);
//            userList.add(lecturer1);
//            userList.add(lecturer2);
//            userList.add(administrativeStaff);
//            return userList;
//        }
//        public void testAddNewResource(){
//            for (Resource resource: testResourceList()) {
//                administrativeStaffService.addNewResource(resource  );
//            }
//        }   // Demonstration method : Add a new resource
//        private List<Resource> testResourceList(){
//            List<Resource> resourceList = new ArrayList<>();
//            Resource equipment1 = new Equipment("Equipment1", LocalTime.of(9,0),LocalTime.of(20,0), Restriction.NonRestriction,"SerialNumber1");
//            Resource equipment2 = new Equipment("Equipment1", LocalTime.of(9,0),LocalTime.of(20,0), Restriction.NonRestriction,"SerialNumber2");
//            Resource equipment3 = new Equipment("Equipment2", null,null, Restriction.NonRestriction,"SerialNumber3");
//            Resource indoorVenue1 = new IndoorVenue("Lab1",LocalTime.of(9,0),LocalTime.of(20,0),Restriction.ApprovalRequired,"Block 1","1R01");
//            Resource indoorVenue2 = new IndoorVenue("Lab2",LocalTime.of(9,0),LocalTime.of(20,0),Restriction.ApprovalRequired,"Block 1","1R02");
//            Resource indoorVenue3 = new IndoorVenue("Lab3",LocalTime.of(9,0),LocalTime.of(18,0),Restriction.ApprovalRequired,"Block 1","2R01");
//            Resource outdoorVenue1 = new OutdoorVenue("Basketball Court",null,null,Restriction.NonRestriction,"Beside field");
//            Resource outdoorVenue2 = new OutdoorVenue("Swimming Pool",null,null,Restriction.Restricted,"Beside main entrance (Construction on going)");
//            resourceList.add(equipment1);
//            resourceList.add(equipment2);
//            resourceList.add(equipment3);
//            resourceList.add(indoorVenue1);
//            resourceList.add(indoorVenue2);
//            resourceList.add(indoorVenue3);
//            resourceList.add(outdoorVenue1);
//            resourceList.add(outdoorVenue2);
//            return resourceList;
//        }
//        public void testNewReservation(){
//            for (Reservation reservation : reservationsTestList()){
//                reservationService.saveReservation(reservation);
//            }
//        }
//        private List<Reservation> reservationsTestList(){
//            List<Reservation> reservations =new ArrayList<>();
//            Reservation reservation1 = reservationService.createNewReservation(
//                    userService.getUserById(1),
//                    resourceService.getResourceByID(2),
//                    LocalDateTime.now(),
//                    LocalDateTime.now().plusHours(1));
//            Reservation reservation2 = reservationService.createNewReservation(
//                    userService.getUserById(1),
//                    resourceService.getResourceByID(6),
//                    LocalDateTime.now().plusDays(2),
//                    LocalDateTime.now().plusWeeks(1));
//            Reservation reservation3 = reservationService.createNewReservation(
//                    userService.getUserById(1),
//                    resourceService.getResourceByID(3),
//                    LocalDateTime.now().minusDays(1),
//                    LocalDateTime.now().plusDays(1));
//            Reservation reservation4 = reservationService.createNewReservation(
//                    userService.getUserById(4),
//                    resourceService.getResourceByID(2),
//                    LocalDateTime.now(),
//                    LocalDateTime.now().plusHours(1));
//            Reservation reservation5 = reservationService.createNewReservation(
//                    userService.getUserById(4),
//                    resourceService.getResourceByID(2),
//                    LocalDateTime.now().plusDays(2),
//                    LocalDateTime.now().plusWeeks(1));
//            reservations.add(reservation1);
//            reservations.add(reservation2);
//            reservations.add(reservation3);
//            reservations.add(reservation4);
//            reservations.add(reservation5);
//            reservations.removeIf(Objects::isNull);
//            return reservations;
//        }
//        public void testNewEvent(){
//            for (Event event: testEventList()){
//                administrativeStaffService.createNewEvent(event);
//            }
//        }
//        private List<Event> testEventList(){
//            List<Event> eventList = new ArrayList<>();
//            List<User> lecturerList = administrativeStaffService.getUserByUserRole(UserRole.Lecturer);
//            List<Venue> labList = venueService.filterVenue(null,"lab",null,null,null);
//            Event event1 = new Event("Lab Open Event",
//                    LocalDateTime.now().plusHours(5),
//                    LocalDateTime.now().plusHours(15),
//                    "The event is open for anybody to visit the labs",
//                    labList,lecturerList);
//            List<User> studentList = administrativeStaffService.getUserByUserRole(UserRole.Student);
//            List<Venue> basketballCourt = venueService.filterVenue(null,"basketball",null,null,null);
//            Event event2 = new Event("Basket ball event",
//                    LocalDateTime.now().plusDays(1),
//                    LocalDateTime.now().plusDays(3),
//                    "The event is open for anybody to play ball",
//                    basketballCourt,studentList);
//            eventList.add(event1);
//            eventList.add(event2);
//            return eventList;
//        }
//    }
}
