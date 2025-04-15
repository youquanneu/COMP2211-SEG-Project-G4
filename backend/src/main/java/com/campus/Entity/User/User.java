package com.campus.Entity.User;

import com.campus.Entity.Event.Event;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Classification.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

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
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;
    @NotNull
    private UserRole userRole;
    @OneToMany
    private List<Reservation> reservations;
    @ManyToMany
    private List<Event> events;
    public void changePassword(String password){
        setPassword(password);
    }
    public void changeUsername(String username){
        setUsername(username);
    }
    public void changeEmail(String email){
        setEmail(email);
    }
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
    public String toString(){
        return String.format(
                """
                        Username   : %s
                        Email      : %s
                        Role       : %s
                        UserId     : %s
                        """,
                getUsername(),
                getEmail(),
                getUserRole(),
                getUserId());
    }
}
