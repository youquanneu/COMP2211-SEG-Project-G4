package com.campus.Service.Event;

import com.campus.Entity.Event.Event;
import com.campus.Entity.User.User;
import com.campus.Repository.Event.EventRepository;
import com.campus.Service.Mail.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Lazy
    @Autowired
    private NotificationService notificationService;
    public Event getEventById(Integer id){
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()){
            throw new RuntimeException("Event not found");
        }
        return event.get();
    }
    public Event saveEvent(Event event){
        return eventRepository.save(event);
    }
    @Transactional
    public void registerForEvent(Event event, User user){
        if (LocalDateTime.now().isAfter(event.getEventEnding())) {
            throw new RuntimeException("Event has been closed");
        }
        else {
            event.addParticipant(user);
            notificationService.generateEventNotification(event,user);
            System.out.println("Register successfully");
        }
    }
    public void rescheduleEvent(){

    }
    public Event cancelledEvent(Event event){
        notificationService.cancelledEventNotification(event);
        return eventRepository.save(event);
    }
}
