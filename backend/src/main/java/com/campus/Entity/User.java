package com.campus.Entity;

import jakarta.persistence.*;
@Entity
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
    private void setEmail(String email) {
        this.email = email;
    }
    private void setPassword(String password) {
        this.password = password;
    }
    private void setUsername(String username) {
        this.username = username;
    }

}
