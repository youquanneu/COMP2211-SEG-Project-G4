package com.campus.Service.Mail;

import com.campus.Entity.Mail.OneTimePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    public OneTimePassword sendOTP(String email){
        OneTimePassword oneTimePassword = new OneTimePassword();
        sendEmail(email,"Your OTP",oneTimePassword.toString());
        return oneTimePassword ;
    }
    public void sendEventReminder(String email, String information){
        sendEmail(email,"Event Reminder",information);
    }
    public void sendReservationReminder(String email, String information){
        sendEmail(email,"Reservation Reminder",information);
    }
    private void sendEmail(String email, String subject, String emailContent){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(emailContent);
        javaMailSender.send(message);
        System.out.println("Email sent successfully");
    }
}
