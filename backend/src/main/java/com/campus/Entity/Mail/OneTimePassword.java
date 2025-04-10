package com.campus.Entity.Mail;

import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class OneTimePassword{
    public static void main(String[] args) {
        OneTimePassword o = new OneTimePassword();
        System.out.println(o);
    }
    public OneTimePassword(){
        setOtpPrefix(randomPrefix());
        setOtpSuffix(randomSuffix());
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
    private String otpPrefix;
    private String otpSuffix;
    public String getOtpPrefix() {
        return otpPrefix;
    }
    public String getOtpSuffix() {
        return otpSuffix;
    }
    private void setOtpPrefix(String otpPrefix) {
        this.otpPrefix = otpPrefix;
    }
    private void setOtpSuffix(String otpSuffix) {
        this.otpSuffix = otpSuffix;
    }
    @Override
    public String toString() {
        return getOtpPrefix()+"-"+getOtpSuffix();
    }
}
