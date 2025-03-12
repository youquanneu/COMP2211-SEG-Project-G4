package com.campus.Service.User;


import com.campus.Entity.User.User;
import com.campus.Repository.User.UserRepository;
import com.campus.Service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailSenderService emailSenderService;
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
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new RuntimeException("Invalid password.");
        }
    }   // Return a user by username and password
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
        try{
            verifyCurrentPassword(user,password);
            changeToNewPassword(user);
        }catch (Exception e){
            System.out.println(e.getMessage());
            changePassword(user);
        }
    }   // Change password for user after verify current password
    private void changeToNewPassword(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("New password: ");
        String newPassword = scanner.nextLine();
        System.out.println("Confirm password: ");
        String confirmationPassword = scanner.nextLine();
        try {
            user.changePassword(passwordEncoder.encode(verifyNewPassword(user, newPassword, confirmationPassword)));
            userRepository.save(user);
            System.out.println("Your password has been successfully changed.");
        }   // Save password changed of user into database after validation
        catch (Exception e) {
            System.out.println(e.getMessage());
            changeToNewPassword(user);
        }
    }   // Change password after new password confirmation
    private void verifyCurrentPassword(User user, String password){
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect current password.");
        }
    }   // Verify current password
    private String verifyNewPassword(User user, String newPassword, String confirmationPassword){
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RuntimeException("New password cannot be the same as current password.");
        }   // Check if new password same as previous password
        else if (!newPassword.equals(confirmationPassword)){
            throw new RuntimeException("Password not matches");
        }   // Check if new password and new password confirmation are same
        return newPassword;
    }   // Return new password after validation checking
    public void forgotPassword(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your email : ");
        String email = scanner.nextLine();
        try {
            User user = findUserByEmail(email);
            String otp = emailSenderService.sendOTP(user.getEmail());
            String input = scanner.nextLine();
            matchesAndChange(user,otp,input);
        }catch (Exception e){
            System.out.println(e.getMessage());
            forgotPassword();
        }
    }
    private User findUserByEmail(String email){
        Optional<User> user = userRepository.findByEmailEqualsIgnoreCase(email);
        if (user.isEmpty()){
            throw new RuntimeException("Invalid Email");
        }
        return user.get();
    }   // Get user by email
    private void matchesAndChange(User user, String givenOTP, String inputOTP){
        if (!givenOTP.equals(inputOTP)){
            throw new RuntimeException("OTP not matches");
        }
        changeToNewPassword(user);
    }   // Change password if OTP verification successful
    @Override   //Still don't know how to apply
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsernameEqualsIgnoreCase(username);
        if (user.isPresent()){
            var userL = user.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userL.getUsername())
                    .password(userL.getPassword())
                    .build();
        }else {
            throw new UsernameNotFoundException(username);
        }
    }
}
