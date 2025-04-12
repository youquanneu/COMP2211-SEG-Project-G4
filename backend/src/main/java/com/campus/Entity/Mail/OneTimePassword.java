package com.campus.Entity.Mail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Random;

@Entity
public class OneTimePassword{
    public OneTimePassword(){}
    public OneTimePassword(String email){
        setOtpPrefix(randomPrefix());
        setOtpSuffix(randomSuffix());
        setEmail(email);
        setCreatedTime(LocalDateTime.now());
    }
    private String randomPrefix(){
        StringBuilder prefix = new StringBuilder();
        Random random = new Random();
        for (int index = 0 ; index <3; index++){
            char element = (char)(65 + random.nextInt(25));
            prefix.append(element);
        }
        return prefix.toString();
    }   // Function : Generate a 3 letters prefix
    private String randomSuffix(){
        StringBuilder suffix = new StringBuilder();
        Random random = new Random();
        for (int index = 3 ; index <9; index++){
            char element = (char)(48 + random.nextInt(10));
            suffix.append(element);
        }
        return suffix.toString();
    }   // Function : Generate a 6 digits suffix
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer otpID;
    private String otpPrefix;
    private String otpSuffix;
    private String email;
    private LocalDateTime createdTime;
    public Integer getOtpID() {
        return otpID;
    }
    public String getOtpPrefix() {
        return otpPrefix;
    }
    public String getOtpSuffix() {
        return otpSuffix;
    }
    public String getEmail() {
        return email;
    }
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    private void setOtpPrefix(String otpPrefix) {
        this.otpPrefix = otpPrefix;
    }
    private void setOtpSuffix(String otpSuffix) {
        this.otpSuffix = otpSuffix;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    private void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
    @Override
    public String toString() {
        return getOtpPrefix()+"-"+getOtpSuffix();
    }
}
