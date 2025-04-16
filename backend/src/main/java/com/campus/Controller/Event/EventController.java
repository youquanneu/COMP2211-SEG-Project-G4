package com.campus.Controller.Event;

import com.campus.Repository.Event.EventRepository;
import com.campus.Service.Event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/Event")
public class EventController {
    private static final Logger logger = Logger.getLogger(EventController.class.getName());
    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;
}
