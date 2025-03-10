package com.campus.Service;


import com.campus.Entity.User;
import com.campus.EntityClassification.UserRole;
import com.campus.Repository.UserRepository;
import jakarta.annotation.Nonnull;
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
    @Nonnull
    private User saveUser(User user){
        return userRepository.save(user);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public List<User> getUserByUserRole(UserRole userRole){
        return userRepository.findUserByUserRole(userRole);
    }

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
            userRole = UserRole.valueOf("Student");
        } else if (type==2) {
            userRole = UserRole.valueOf("Lecturer");
        }else {
            userRole = UserRole.valueOf("AdministrativeStaff");
        }
        User u = registerNewUser(username,email,password,userRole);
        System.out.println(u.toString());
    }
    private User registerNewUser(String username, String email, String password, UserRole userRole) {
        Optional<User> existingUser = userRepository.findByUsernameOrEmailEqualsIgnoreCase(email, username);
        if (existingUser.isPresent()) {
            System.out.println("User with this email or username already exists.");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, email, encodedPassword, userRole);
        return saveUser(newUser);
    }
    public void login(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input username : ");
        String username = scanner.nextLine();
        System.out.println("Input password : ");
        String password = scanner.nextLine();
        try {
            User user = loginAsUser(username, password);
            System.out.println(user.getEmail());
            System.out.println(user.getUserRole());
        }catch (Exception e){
            System.out.println(e.getMessage());
            login();
        }
    }
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
    }

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
//    public void changePassword() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter your current password: ");
//        String currentPassword = scanner.nextLine();
//
//        // Retrieve the currently logged-in user (you can get the logged-in user from a session, context, etc.)
//        User user = getCurrentLoggedInUser();
//
//        // Verify the current password
//        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
//            throw new RuntimeException("Incorrect current password.");
//        }
//
//        // Ask for a new password
//        System.out.println("Enter your new password: ");
//        String newPassword = scanner.nextLine();
//
//        // Encode the new password
//        String encodedPassword = passwordEncoder.encode(newPassword);
//
//        // Update the password in the user object
//        user.setPassword(encodedPassword);
//        userRepository.save(user);
//
//        System.out.println("Your password has been successfully changed.");
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
