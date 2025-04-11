package com.campus.DataTransferObject.Mail;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OneTimePasswordDTO {
    public OneTimePasswordDTO (){}
    public OneTimePasswordDTO(String email,String otpPrefix ,String enteredOTP){
        setEmail(email);
        setOtpPrefix(otpPrefix);
        setEnteredOTP(enteredOTP);
    }
    @JsonProperty
    private String email;
    @JsonProperty
    private String otpPrefix;
    @JsonProperty
    private String enteredOTP;
    public String getEmail() {
        return email;
    }
    public String getOtpPrefix() {
        return otpPrefix;
    }
    public String getEnteredOTP() {
        return enteredOTP;
    }
    private void setEmail(String email) {
        this.email = email;
    }
    private void setOtpPrefix(String otpPrefix) {
        this.otpPrefix = otpPrefix;
    }
    private void setEnteredOTP(String enteredOTP) {
        this.enteredOTP = enteredOTP;
    }
}
