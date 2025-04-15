package com.campus.Service.Mail;

import com.campus.Entity.Event.EmergencyCase;
import com.campus.Entity.Event.Event;
import com.campus.Entity.Mail.EventNotification;
import com.campus.Entity.Mail.OneTimePassword;
import com.campus.Entity.Mail.ReservationNotification;
import com.campus.Entity.User.AdministrativeStaff;
import com.campus.Entity.User.User;
import com.campus.Service.User.AdministrativeStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private OneTimePasswordService oneTimePasswordService;
    @Lazy
    @Autowired
    private AdministrativeStaffService administrativeStaffService;
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
    public void sendEventCancelNews(User user, Event event){
        sendEmail(user.getEmail(),"The following event has been cancelled",event.toString());
    }
    public void reportNewEmergency(EmergencyCase emergencyCase){
        List<AdministrativeStaff> admins = administrativeStaffService.getAllAdmin();
        for (User user : admins){
            sendEmail(user.getEmail(), "Emergency Case Reported",emergencyCase.toString());
        }
    }
    public void alertEmergency(EmergencyCase emergencyCase){
        List<User> users = administrativeStaffService.getAllUsers();
        for (User user : users){
            sendEmail(user.getEmail(), "Emergency Case To Be Mention",emergencyCase.toString());
        }
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
