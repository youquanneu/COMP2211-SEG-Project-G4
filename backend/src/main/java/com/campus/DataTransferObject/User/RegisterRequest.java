package com.campus.DataTransferObject.User;

import com.campus.Classification.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest {
    public RegisterRequest(){}
    public RegisterRequest(String username, String email, UserRole userRole){
        setUsername(username);
        setEmail(email);
        setUserRole(userRole);
    }
    @JsonProperty
    private String username;
    @JsonProperty
    private String email;
    @JsonProperty("userRole")
    private UserRole userRole;
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public UserRole getUserRole() {
        return userRole;
    }
    private void setUsername(String username) {
        this.username = username;
    }
    private void setEmail(String email) {
        this.email = email;
    }
    private void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
