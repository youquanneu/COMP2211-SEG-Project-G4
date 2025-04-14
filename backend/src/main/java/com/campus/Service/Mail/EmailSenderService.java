package com.campus.Service.Mail;

import com.campus.Entity.Mail.EventNotification;
import com.campus.Entity.Mail.OneTimePassword;
import com.campus.Entity.Mail.ReservationNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private OneTimePasswordService oneTimePasswordService;
    public String sendOTP(String email){
        OneTimePassword oneTimePassword = oneTimePasswordService.generateOTP(email);
        sendEmail(email,"Your OTP",oneTimePassword.toString());
        return oneTimePassword.getOtpPrefix() ;
    }
    public void sendEventReminder(EventNotification eventNotification){
        sendEmail(eventNotification.getRecipient().getEmail(),"Event Reminder",eventNotification.toString());
    }
    public void sendReservationReminder(ReservationNotification reservationNotification){
        sendEmail(reservationNotification.getRecipient().getEmail(),"Reservation Reminder",reservationNotification.toString());
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
