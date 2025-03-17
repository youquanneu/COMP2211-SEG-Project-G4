package com.campus.Service.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    public String sendOTP(String email){
        String otp = otpCreator();
        sendEmail(email,"Your OTP",otp);
        return otp;
    }
    public void sendEventReminder(String email, String information){
        sendEmail(email,"Event Reminder",information);
    }
    public void sendReservationReminder(String email, String information){
        sendEmail(email,"Reservation Reminder",information);
    }
    private String otpCreator(){
        StringBuilder otp = new StringBuilder();
        Random random = new Random();
        for (int index = 0 ; index <3; index++){
            char element = (char)(65 + random.nextInt(25));
            otp.append(element);
        }
        for (int index = 3 ; index <9; index++){
            char element = (char)(48 + random.nextInt(10));
            otp.append(element);
        }
        return otp.toString();
    }   // Generate an otp with 3 char and 6 int
    private void sendEmail(String email, String subject, String emailContent){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(emailContent);
        javaMailSender.send(message);
        System.out.println("Email sent successfully");
    }
}
