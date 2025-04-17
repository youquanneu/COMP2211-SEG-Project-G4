package com.campus.Configuration;

import com.campus.Classification.Restriction;
import com.campus.Classification.UserRole;
import com.campus.Entity.Resource.Equipment;
import com.campus.Entity.Resource.IndoorVenue;
import com.campus.Entity.Resource.OutdoorVenue;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.AdministrativeStaff;
import com.campus.Entity.User.Lecturer;
import com.campus.Entity.User.Student;
import com.campus.Entity.User.User;
import com.campus.Service.Resource.ResourceService;
import com.campus.Service.User.AdministrativeStaffService;
import com.campus.Service.User.UserService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DemoCode {
    static class UserServiceDemo{
        private UserService userService;
        public void demoChangePassword(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input username : ");
            String username = scanner.nextLine();
            System.out.println("Input password : ");
            String password = scanner.nextLine();
            try {
                changePassword(userService.loginByUsername(username, password));
            }catch (Exception e){
                System.out.println(e.getMessage());
                demoChangePassword();
            }
        }   // Demonstration method: Change password
        private void changePassword(User user) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your current password: ");
            String password = scanner.nextLine();
            System.out.println("New password: ");
            String newPassword = scanner.nextLine();
            System.out.println("Confirm password: ");
            String confirmationPassword = scanner.nextLine();
            try{
                userService.changePassword(user,password,newPassword,confirmationPassword);
            }catch (Exception e){
                System.out.println(e.getMessage());
                changePassword(user);
            }
        }   // Demonstration method: Change password of the user
        public void demoForgotPassword(){
            try {
                // Page 1 : Take email and send OTP
                Scanner scanner = new Scanner(System.in);
                System.out.println("Your email : ");
                String email = scanner.nextLine();
                // Page 2 : Get user input of OTP
                System.out.println("Your OTP : ");
                String inputOTP = scanner.nextLine();
                User user = userService.forgotPassword(email,inputOTP);
                // Page 3 : Let user change of password
                System.out.println("New Password : ");
                String newPassword = scanner.nextLine();
                System.out.println("Confirm Password : ");
                String confirmationPassword = scanner.nextLine();
                userService.changeToNewPassword(user,newPassword,confirmationPassword);
            }catch (Exception e){
                System.out.println(e.getMessage());
                demoForgotPassword();
            }
        }   // Demonstration method: Forgot password
        public void demoLogin(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input username : ");
            String username = scanner.nextLine();
            System.out.println("Input password : ");
            String password = scanner.nextLine();
            try {
                User user = userService.loginByUsername(username, password);
                System.out.println(user);
            }catch (Exception e){
                System.out.println(e.getMessage());
                demoLogin();
            }
        }
        public void testLogin(){
            String username = "user1";
            String password = "password1";
            try {
                User user = userService.loginByUsername(username, password);
                System.out.println(user);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }   // Demonstration method: Login as user by username and password
        public void testChangePassword(){
            User user = userService.getUserById(1);
            String oldPassword = user.getPassword();
            String newPassword = "newPassword";
            String confirmPwd  = "newPassword";
            try {
                userService.changePassword(user,oldPassword,newPassword,confirmPwd);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    static class demoAdminService{
        private AdministrativeStaffService administrativeStaffService;
        private UserService userService;
        private ResourceService resourceService;
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
        public void testModifyUserInformation(){
            User user = userService.getUserById(1);
            administrativeStaffService.modifyUsername(user,"Alex");
            administrativeStaffService.modifyEmail(user,"Alex@gmail.com");
        }
        public void testDeleteUser(){
            User user = userService.getUserById(3);
            administrativeStaffService.deleteUser(user);
        }
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
        public void testChangeInfo(){
            Resource resource = resourceService.getResourceByID(1);
            administrativeStaffService.changeResourceName(resource,"Computer");
            administrativeStaffService.changeOpenTime(resource,null);
            administrativeStaffService.changeCloseTime(resource,null);
            administrativeStaffService.changeRestriction(resource,Restriction.ApprovalRequired);
        }
        public void testDeleteResource(){
            administrativeStaffService.deleteResource(resourceService.getResourceByID(4));
        }
    }
}
