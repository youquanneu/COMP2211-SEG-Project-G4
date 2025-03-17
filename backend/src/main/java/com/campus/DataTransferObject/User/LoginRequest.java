package com.campus.DataTransferObject.User;

public class LoginRequest {
    public LoginRequest(){}
    public LoginRequest(String username, String password){
        setUsername(username);
        setPassword(password);
    }
    private String username;
    private String password;
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    private void setUsername(String username) {
        this.username = username;
    }
    private void setPassword(String password) {
        this.password = password;
    }
}
