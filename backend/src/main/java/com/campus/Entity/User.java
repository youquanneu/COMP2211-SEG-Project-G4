package com.campus.Entity;

import com.campus.EntityClassification.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {
    public User() {}
    public User(String username, String email, String password, UserRole userRole){
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setUserRole(userRole);
    }
    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // Auto Increment
    private Integer userId;
    private String username;
    private String email;
    private String password;
    private UserRole userRole;
    @OneToMany(mappedBy = "booker", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
    public Integer getUserId() {
        return userId;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public UserRole getUserRole() {
        return userRole;
    }
    private void setEmail(String email) {
        this.email = email;
    }
    private void setPassword(String password) {
        this.password = password;
    }
    private void setUsername(String username) {
        this.username = username;
    }
    private void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
class Student extends User{}
class Lecturer extends User{}
class AdministrativeStaff extends User{ }