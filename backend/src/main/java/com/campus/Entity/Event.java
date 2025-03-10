package com.campus.Entity;

import jakarta.persistence.*;
import org.w3c.dom.Text;

import java.time.LocalDateTime;

@Entity
public class Event {
    public Event(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDateTime eventStarting;
    private LocalDateTime eventEnding;
    private String eventDescription;
}
