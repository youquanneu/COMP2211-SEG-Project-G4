package com.campus.Entity.Mail;

import com.campus.Entity.Event.Event;
import com.campus.Entity.User.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity

public class EventNotification extends Notification{
    public EventNotification(){}
    public EventNotification(User participant,
                             Event event,
                             LocalDateTime notificationTime){
        super(participant, notificationTime);
        setEvent(event);
    }
    @ManyToOne
    private Event event;
    public Event getEvent() {
        return event;
    }
    private void setEvent(Event event) {
        this.event = event;
    }
}
