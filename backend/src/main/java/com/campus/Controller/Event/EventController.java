package com.campus.Controller.Event;

import com.campus.DataTransferObject.Event.EventDTO;
import com.campus.DataTransferObject.Event.RegisterForEventRequest;
import com.campus.DataTransferObject.User.UserEmailDTO;
import com.campus.Entity.Event.Event;
import com.campus.Entity.User.User;
import com.campus.Repository.Event.EventRepository;
import com.campus.Service.Event.EventService;
import com.campus.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user/event")
public class EventController {
    private static final Logger logger = Logger.getLogger(EventController.class.getName());
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;
    @GetMapping("/getAllEvent")
    public ResponseEntity<?> getAllEvent() {
        logger.info("Getting events processing : ");
        try {
            List<Event> events = eventService.getAllEvent();
            for (Event event : events) {
                event.getVenues().size();
            }
            List<EventDTO> eventDTOS = EventDTO.listMapper(events);
            logger.info("Get events : " + eventDTOS );
            return ResponseEntity.ok(eventDTOS);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PostMapping("/getMyEvent")
    public ResponseEntity<?> getMyEvent(@RequestBody UserEmailDTO userEmailDTO) {
        logger.info("Processing getMyEvent : " + userEmailDTO.getEmail());
        try {
            User user = userService.getUserByEmail(userEmailDTO.getEmail());
            List<Event> events = eventService.getMyRelateEvent(user);
            for (Event event : events) {
                event.getVenues().size();
            }
            List<EventDTO> eventDTOS = EventDTO.listMapper(events);
            logger.info("Get events : " + eventDTOS );
            return ResponseEntity.ok(eventDTOS);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PostMapping("/registerForEvent")
    public ResponseEntity<?> registerForEvent(@RequestBody RegisterForEventRequest registerForEventRequest) {
        logger.info("Processing registerForEvent : " + registerForEventRequest.getEmail());
        try {
            User user = userService.getUserByEmail(registerForEventRequest.getEmail());
            Event event = eventService.getEventById(registerForEventRequest.getEventDTO().getEventId());
            EventDTO eventDTO = EventDTO.mapper(eventService.registerForEvent(event,user));
            logger.info("Get events : " + eventDTO );
            return ResponseEntity.ok(eventDTO);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
