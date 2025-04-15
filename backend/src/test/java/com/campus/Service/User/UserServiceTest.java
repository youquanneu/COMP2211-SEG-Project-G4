package com.campus.Service.User;

import com.campus.Entity.User.User;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Scanner;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    private UserService userService;
    @Test
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
    @Test
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
    @Test
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
    @Test
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
    @Test
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
