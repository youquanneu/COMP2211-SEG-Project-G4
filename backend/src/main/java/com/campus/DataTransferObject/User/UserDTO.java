package com.campus.DataTransferObject.User;

import com.campus.Classification.UserRole;
import com.campus.Entity.User.User;
import com.campus.Service.User.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDTO {
    public UserDTO(){}
    public UserDTO(Integer userId,String username,
                   String email, UserRole userRole){
        setUserId(userId);
        setUsername(username);
        setEmail(email);
        setUserRole(userRole);
    }
    public static UserDTO mapper(User user){
        return new UserDTO(user.getUserId(), user.getUsername(), user.getEmail(), user.getUserRole());
    }
    @JsonProperty
    private Integer userId;
    @JsonProperty
    private String username;
    @JsonProperty
    private String email;
    @JsonProperty("UserRole")
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
