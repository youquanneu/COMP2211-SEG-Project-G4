package com.campus.DataTransferObject.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResetPasswordRequest {
    public ResetPasswordRequest(){}
    public ResetPasswordRequest(
            String email,
            String newPassword,
            String confirmPassword){
        setEmail(email);
        setNewPassword(newPassword);
        setConfirmPassword(confirmPassword);
    }
    @JsonProperty
    private String email;
    @JsonProperty
    private String newPassword;
    @JsonProperty
    private String confirmPassword;
    public String getEmail() {
        return email;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    private void setEmail(String email) {
        this.email = email;
    }
    private void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    private void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
