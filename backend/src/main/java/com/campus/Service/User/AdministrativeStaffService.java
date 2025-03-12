package com.campus.Service.User;

import com.campus.Entity.User.User;
import com.campus.EntityClassification.UserRole;
import com.campus.Repository.User.AdministrativeStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class AdministrativeStaffService{
    @Autowired
    private AdministrativeStaffRepository administrativeStaffRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
        Optional<User> existingUser = administrativeStaffRepository.findByUsernameEqualsIgnoreCaseOrEmailEqualsIgnoreCase(username, email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this email or username already exists.");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, email, encodedPassword, userRole);
        return saveUser(newUser);
    }   // Register a new user only if non-duplicate email or username
    private User saveUser(User user){
        return administrativeStaffRepository.save(user);
    }   // Insert a new user into database
    public void allUser(){
        List<User> userList = getAllUsers();
        for (User user : userList) {
            System.out.println(user.toString());
        }
    }   // Demonstration method: List out all user
    private List<User> getAllUsers(){
        return administrativeStaffRepository.findAll();
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
        return administrativeStaffRepository.findUserByUserRole(userRole);
    }   // Get a list of user base on role
}
