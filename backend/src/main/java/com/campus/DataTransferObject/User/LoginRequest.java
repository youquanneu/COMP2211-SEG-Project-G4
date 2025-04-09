package com.campus.DataTransferObject.User;

public class LoginRequest {
    public LoginRequest(){}
    public LoginRequest(String email, String password){
        setEmail(email);
        setPassword(password);
    }
    private String email;
    private String password;
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    private void setEmail(String email) {
        this.email = email;
    }
    private void setPassword(String password) {
        this.password = password;
    }
}
