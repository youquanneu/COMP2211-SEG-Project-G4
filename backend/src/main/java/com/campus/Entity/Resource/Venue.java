package com.campus.Entity.Resource;

import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import com.campus.Entity.Event.Event;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;

import java.time.LocalTime;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Venue extends Resource{
    public Venue(){}
    public Venue(String resourceName,
                 LocalTime openTime, LocalTime closeTime,
                 Restriction restriction,
                 ResourceCategory resourceCategory){
        super(resourceName, openTime, closeTime, restriction,resourceCategory);
    }
    @ManyToMany
    private List<Event> events;
}
