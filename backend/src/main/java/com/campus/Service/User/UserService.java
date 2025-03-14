package com.campus.Service.User;


import com.campus.Entity.User.User;

import com.campus.Repository.User.UserRepository;
import com.campus.Service.Mail.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailSenderService emailSenderService;
    public User getUserById(Integer id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return user.get();
    }  
    private void verifyCurrentPassword(User user, String password){
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect current password.");
        }
    }   // Verify current password
    public void login(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input username : ");
        String username = scanner.nextLine();
        System.out.println("Input password : ");
        String password = scanner.nextLine();
        try {
            User user = loginAsUser(username, password);
            System.out.println(user);
        }catch (Exception e){
            System.out.println(e.getMessage());
            login();
        }
    }   // Demonstration method: Login as user by username and password
    private User loginAsUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsernameEqualsIgnoreCase(username);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid username.");
        }
        User user = userOpt.get();
        verifyCurrentPassword(user,password);
        return user;
    }   // Function: Return a user by username and password
    public void changePassword(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input username : ");
        String username = scanner.nextLine();
        System.out.println("Input password : ");
        String password = scanner.nextLine();
        try {
            changePassword(loginAsUser(username, password));
        }catch (Exception e){
            System.out.println(e.getMessage());
            changePassword();
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
            changePassword(user,password,newPassword,confirmationPassword);
        }catch (Exception e){
            System.out.println(e.getMessage());
            changePassword(user);
        }
    }   // Demonstration method: Change password after user login successful
    private void changePassword(User user, String currentPassword, String newPassword, String confirmationPassword) {
        try{
            verifyCurrentPassword(user,currentPassword);
            changeToNewPassword(user,newPassword,confirmationPassword);
        }catch (Exception e){
            System.out.println(e.getMessage());
            changePassword(user);
        }
    }   // Function: Change password for user
    private void changeToNewPassword(User user,String newPassword, String confirmationPassword) {
        try {
            System.out.println("New password:       ******");
            System.out.println("Confirm password:   ******");
            user.changePassword(passwordEncoder.encode(verifyNewPassword(user, newPassword, confirmationPassword)));
            userRepository.save(user);
            System.out.println("Your password has been successfully changed.");
        }   // Save password changed of user into database after validation
        catch (Exception e) {
            System.out.println(e.getMessage());
            changePassword(user);
        }
    }   // Change password after new verification
    private String verifyNewPassword(User user, String newPassword, String confirmationPassword){
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RuntimeException("New password cannot be the same as current password.");
        }   // Check if new password same as previous password
        else if (!newPassword.equals(confirmationPassword)){
            throw new RuntimeException("Password not matches");
        }   // Check if new password and new password confirmation are same
        return newPassword;
    }   // Return new password after validation checking
    public void forgotPasswordDemo(){
        try {
            // Page 1 : Take email and send OTP
            Scanner scanner = new Scanner(System.in);
            System.out.println("Your email : ");
            String email = scanner.nextLine();
            // Page 2 : Get user input of OTP
            System.out.println("Your OTP : ");
            String inputOTP = scanner.nextLine();
            User user = forgotPassword(email,inputOTP);
            // Page 3 : Let user change of password
            System.out.println("New Password : ");
            String newPassword = scanner.nextLine();
            System.out.println("Confirm Password : ");
            String confirmationPassword = scanner.nextLine();
            changeToNewPassword(user,newPassword,confirmationPassword);
        }catch (Exception e){
            System.out.println(e.getMessage());
            forgotPasswordDemo();
        }
    }   // Demonstration method: Forgot password
    private User forgotPassword(String email,String inputOTP){
        User user = findUserByMatchingEmail(email);
        String givenOTP = emailSenderService.sendOTP(email);
        matchOTP(givenOTP,inputOTP);
        return user;
    }
    private User findUserByMatchingEmail(String email){
        Optional<User> user = userRepository.findByEmailEqualsIgnoreCase(email);
        if (user.isEmpty()){
            throw new RuntimeException("Invalid Email");
        }
        return user.get();
    }   // Get user by email
    private void matchOTP(String givenOTP, String inputOTP){
        if (!givenOTP.equals(inputOTP)){
            throw new RuntimeException("OTP not matches");
        }
    }   // Change password if OTP verification successful
}
