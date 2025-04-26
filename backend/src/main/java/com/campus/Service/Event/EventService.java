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
import java.util.ArrayList;
import java.util.List;
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
    public List<Event> getAllEvent(){
        return eventRepository.findAll();
    }
    @Transactional
    public List<Event> getMyParticipateEvent(User user){
        return eventRepository.findEventsByParticipantContaining(user);
    }
    @Transactional
    public List<Event> getMyOrganizeEvent(User user){
        return eventRepository.findEventsByOrganizerContaining(user);
    }
    @Transactional
    public List<Event> getMyRelateEvent(User user){
        return eventRepository.findEventsByParticipantContainingOrOrganizerContaining(user,user);
    }
    @Transactional
    public Event registerForEvent(Event event, User user){
        if (LocalDateTime.now().isAfter(event.getEventEnding())) {
            throw new RuntimeException("Event has been closed");
        }
        List<Integer> participantId = new ArrayList<>();
        for (User participant : event.getParticipant()){
            participantId.add(participant.getUserId());
        }
        if (participantId.contains(user.getUserId())) {
            throw new RuntimeException("User already registered for this event");
        }
        event.addParticipant(user);
        notificationService.generateEventNotification(event,user);
        return eventRepository.save(event);
    }
    public void rescheduleEvent(Event event, LocalDateTime eventStartingTime, LocalDateTime eventEndingTime){

    }
    public Event cancelledEvent(Event event){
        notificationService.cancelledEventNotification(event);
        return eventRepository.save(event);
    }
}
