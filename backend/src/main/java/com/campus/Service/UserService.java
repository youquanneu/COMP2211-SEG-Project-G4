package com.campus.Service;


import com.campus.Entity.User;
import com.campus.EntityClassification.UserRole;
import com.campus.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void register(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input username : ");
        String username = scanner.nextLine();
        System.out.println("Input email : ");
        String email = scanner.nextLine();
        System.out.println("Input password : ");
        String password = scanner.nextLine();
        System.out.println("Input type: 1.Student 2.Lecturer 3.AdministrativeStaff ");
        UserRole userRole;
        int type = scanner.nextInt();
        if (type ==1){
            userRole = UserRole.Student;
        } else if (type==2) {
            userRole = UserRole.Lecturer;
        }else {
            userRole = UserRole.AdministrativeStaff;
        }
        try {
            User u = registerNewUser(username, email, password, userRole);
            System.out.println("User register successful : \n" + u);
        }catch (Exception e){
            System.out.println(e.getMessage());
            register();
        }
    }   // Demonstration method: Register a new user
    private User registerNewUser(String username, String email, String password, UserRole userRole) {
        Optional<User> existingUser = userRepository.findByUsernameEqualsIgnoreCaseOrEmailEqualsIgnoreCase(username, email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this email or username already exists.");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, email, encodedPassword, userRole);
        return saveUser(newUser);
    }   // Register a new user only if non-duplicate email or username
    private User saveUser(User user){
        return userRepository.save(user);
    }   // Insert a new user into database
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
    public void allUser(){
        List<User> userList = getAllUsers();
        for (User user : userList) {
            System.out.println(user.toString());
        }
    }   // Demonstration method: List out all user
    private List<User> getAllUsers(){
        return userRepository.findAll();
    }   // Get a list of all user
    public void userByRole() {
        System.out.println("Select type: 1.Student 2.Lecturer 3.AdministrativeStaff ");
        UserRole userRole;
        Scanner scanner = new Scanner(System.in);
        int type = scanner.nextInt();
        if (type ==1){
            userRole = UserRole.Student;
        } else if (type==2) {
            userRole = UserRole.Lecturer;
        }else {
            userRole = UserRole.AdministrativeStaff;
        }
        List<User> roleList = getUserByUserRole(userRole);
        for (User user : roleList) {
            System.out.println(user.toString());
        }
    }   // Demonstration method: List out user by role
    private List<User> getUserByUserRole(UserRole userRole){
        return userRepository.findUserByUserRole(userRole);
    }   // Get a list of user base on role
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
//    public void forgotPassword() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter your email address: ");
//        String email = scanner.nextLine();
//        Optional<User> userOpt = userRepository.findByEmailEqualsIgnoreCase(email);
//        if (userOpt.isEmpty()) {
//            throw new RuntimeException("No user found with this email.");
//        }
//        User user = userOpt.get();
//
//        // Generate a reset token (you could store this token in the database with an expiry time)
//        String resetToken = UUID.randomUUID().toString();
//
//        // Save the reset token (in your database or any temporary storage)
//        user.setResetToken(resetToken);
//        userRepository.save(user);
//
//        // Send email with the reset token (implement your email service)
//        sendPasswordResetEmail(user.getEmail(), resetToken);
//
//        System.out.println("A password reset link has been sent to your email.");
//    }
//    private void sendPasswordResetEmail(String email, String token) {
//        // Email sending logic (using a library such as JavaMail, for example)
//        String resetLink = "http://example.com/reset-password?token=" + token;
//        String subject = "Password Reset Request";
//        String body = "Click the link below to reset your password:\n" + resetLink;
//
//        // Implement the email sending here (using SMTP, JavaMail API, etc.)
//        // For example:
//        // emailService.sendEmail(email, subject, body);
//    }
//    public void resetPassword() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter the reset token from your email: ");
//        String token = scanner.nextLine();
//
//        Optional<User> userOpt = userRepository.findByResetToken(token);
//        if (userOpt.isEmpty()) {
//            throw new RuntimeException("Invalid or expired token.");
//        }
//
//        User user = userOpt.get();
//
//        System.out.println("Enter your new password: ");
//        String newPassword = scanner.nextLine();
//
//        // Encode the new password
//        String encodedPassword = passwordEncoder.encode(newPassword);
//
//        // Update the user's password
//        user.setPassword(encodedPassword);
//        user.setResetToken(null); // Clear the reset token
//        userRepository.save(user);
//
//        System.out.println("Your password has been successfully reset.");
//    }

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
