package com.campus.Controller.Event;

import com.campus.DataTransferObject.Event.EventDTO;
import com.campus.Entity.Event.Event;
import com.campus.Repository.Event.EventRepository;
import com.campus.Service.Event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user/event")
public class EventController {
    private static final Logger logger = Logger.getLogger(EventController.class.getName());
    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;
    @GetMapping("/getAllEvent")
    public ResponseEntity<?> getAllEvent() {
        logger.info("Getting events processing : ");
        try {
            List<Event> events = eventService.getAllEvent();
            // Explicitly fetch the venues within the transactional context
            for (Event event : events) {
                // Trigger the loading of the lazy-loaded 'venues' collection
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
}
