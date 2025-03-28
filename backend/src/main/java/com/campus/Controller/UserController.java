package com.campus.Controller;

import com.campus.Classification.Status;
import com.campus.Classification.UserRole;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.AdministrativeStaff;
import com.campus.Entity.User.Lecturer;
import com.campus.Entity.User.Student;
import com.campus.Entity.User.User;
import com.campus.Repository.Reservation.ReservationRepository;
import com.campus.Service.Reservation.ReservationService;
import com.campus.Service.Resource.EquipmentService;
import com.campus.Service.Resource.IndoorVenueService;
import com.campus.Service.Resource.ResourceService;
import com.campus.Service.User.AdministrativeStaffService;
import com.campus.Service.User.UserService;
import org.jetbrains.annotations.TestOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController implements CommandLineRunner{
    @Override
    public void run(String... args) throws Exception {
        testRegisterUser();
    }
    @Autowired
    private AdministrativeStaffService administrativeStaffService;
    @Autowired
    private UserService userService;
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
    private void testModifyUserInformation(){
        User user = userService.getUserById(1);
        administrativeStaffService.modifyUsername(user,"Alex");
        administrativeStaffService.modifyEmail(user,"Alex@gmail.com");
    }
    private void testDeleteUser(){
        User user = userService.getUserById(2);
        administrativeStaffService.deleteUser(user);
    }
    private void testFilterUsers(){
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
    private void testGetByRole(){
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
}
