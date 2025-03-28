package com.campus.Service.User;

import com.campus.Entity.User.AdministrativeStaff;
import com.campus.Entity.User.Lecturer;
import com.campus.Entity.User.Student;
import com.campus.Entity.User.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AdministrativeStaffServiceTest {

    private AdministrativeStaffService administrativeStaffService;
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

//    public void allUser(){
//        List<User> userList = administrativeStaffService.getAllUsers();
//        for (User user : userList) {
//            System.out.println(user.toString());
//        }
//    }   // Demonstration method: List out all user
//    public void userByRole() {
//        System.out.println("Select type: 1.Student 2.Lecturer 3.AdministrativeStaff ");
//        UserRole userRole;
//        Scanner scanner = new Scanner(System.in);
//        int type = scanner.nextInt();
//        if (type ==1){
//            userRole = UserRole.Student;
//        } else if (type==2) {
//            userRole = UserRole.Lecturer;
//        }else {
//            userRole = UserRole.AdministrativeStaff;
//        }
//        List<User> roleList = administrativeStaffService.getUserByUserRole(userRole);
//        for (User user : roleList) {
//            System.out.println(user.toString());
//        }
//    }   // Demonstration method: List out user by role
}
