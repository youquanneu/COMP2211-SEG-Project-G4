package com.campus.DataTransferObject.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {
    public LoginRequest(){}
    public LoginRequest(String email, String password){
        setEmail(email);
        setPassword(password);
    }
    @JsonProperty
    private String email;
    @JsonProperty
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
