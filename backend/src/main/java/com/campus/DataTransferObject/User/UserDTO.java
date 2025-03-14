package com.campus.DataTransferObject.User;
public class UserDTO {
    public UserDTO(){}
    public UserDTO(String username, String email){
        setUsername(username);
        setEmail(email);
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    private void setUsername(String username) {
        this.username = username;
    }
    private void setEmail(String email) {
        this.email = email;
    }
    private String username;
    private String email;
}
