package com.campus.Entity;

import jakarta.persistence.*;

@Entity
public class Event {
    public Event(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

}
