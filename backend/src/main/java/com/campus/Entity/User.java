package com.campus.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_role",discriminatorType = DiscriminatorType.STRING)
public class User {
    public User() {
    }
    public User(String username, String email, String password){
        setUsername(username);
        setEmail(email);
        setPassword(password);
    }
    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // Auto Increment
    private Integer id;
    private String username;
    private String email;
    private String password;
    private UserRole userRole;
    public Integer getId() {
        return id;
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
    UserRole getUserRole() {
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
@Entity
@DiscriminatorValue("Student")
class Student extends User{
}
@Entity
@DiscriminatorValue("Lecturer")
class Lecturer extends User{

}
@Entity
@DiscriminatorValue("AdministrativeStaff")
class AdministrativeStaff extends User{

}
enum UserRole{

}