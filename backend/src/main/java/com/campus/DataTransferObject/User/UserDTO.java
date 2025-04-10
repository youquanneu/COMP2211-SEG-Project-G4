package com.campus.DataTransferObject.User;

import com.campus.Classification.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class UserDTO {
    public UserDTO(){}
    public UserDTO(Integer userId,String username,
                   String email, UserRole userRole){
        setUserId(userId);
        setUsername(username);
        setEmail(email);
        setUserRole(userRole);
    }
    @JsonProperty
    private Integer userId;
    @JsonProperty
    private String username;
    @JsonProperty
    private String email;
    @JsonProperty
    private UserRole userRole;
    public UserRole getUserRole() {
        return userRole;
    }
    public Integer getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    private void setUsername(String username) {
        this.username = username;
    }
    private void setEmail(String email) {
        this.email = email;
    }
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
