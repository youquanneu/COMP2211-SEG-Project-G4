package com.campus.Entity.Mail;

import com.campus.Classification.Status;
import com.campus.Entity.User.User;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Notification {
    public Notification(){}
    public Notification(User recipient,
                        LocalDateTime notificationTime){
        setRecipient(recipient);
        setNotificationTime(notificationTime);
        setStatus(Status.Pending);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;
    @NotNull
    @ManyToOne
    private User recipient;
    @NotNull
    private LocalDateTime notificationTime;
    @NotNull
    private Status status;
    public void notificationSend(){
        setStatus(Status.Sent);
    }
    public void notificationCancelled(){
        setStatus(Status.Cancelled);
    }
    public User getRecipient() {
        return recipient;
    }
    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }
    public Status getStatus() {
        return status;
    }
    private void setRecipient(User recipient) {
        this.recipient = recipient;
    }
    private void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }
    private void setStatus(Status status) {
        this.status = status;
    }
}