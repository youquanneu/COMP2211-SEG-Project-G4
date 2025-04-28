package com.campus.Service.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulingService {
    private final OneTimePasswordService oneTimePasswordService;
    public SchedulingService(OneTimePasswordService oneTimePasswordService) {
        this.oneTimePasswordService = oneTimePasswordService;
    }
    @Autowired
    private NotificationService notificationService;
    @Scheduled(fixedDelay = 300000)
    private void removeExpiryOTP() {
        oneTimePasswordService.deleteExpiredOTP();
    }
    @Scheduled(fixedDelay = 60000)
    private void sendReservationNotification() {
        notificationService.sendReservationNotification();
    }
    @Scheduled(fixedDelay = 300000)
    private void sendEventNotification() {
        notificationService.sendEventNotification();
    }
}