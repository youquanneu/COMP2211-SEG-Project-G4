package com.campus.Service.Event;

import com.campus.Entity.Event.Event;
import com.campus.Entity.Resource.IndoorVenue;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.User;
import com.campus.Repository.Event.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
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
    public void registerForEvent(Integer eventId, User user){
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()){
            throw new RuntimeException("Event data inconsistency found");
        }
        Event eventGet = eventOptional.get();
        if (LocalDateTime.now().isAfter(eventGet.getEventEnding())) {
            throw new RuntimeException("Event has been closed");
        }
        else {
            eventGet.addParticipant(user);
            System.out.println("Register successfully");
        }
    }
}
