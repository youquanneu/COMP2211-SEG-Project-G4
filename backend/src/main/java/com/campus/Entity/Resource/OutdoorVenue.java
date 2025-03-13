package com.campus.Entity.Resource;

import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import jakarta.persistence.Entity;

import java.time.LocalTime;

@Entity
public class OutdoorVenue extends Resource{
    public OutdoorVenue(){}
    public OutdoorVenue(String resourceName,
                        LocalTime openTime, LocalTime closeTime,
                        Restriction restriction){
        super(resourceName, openTime, closeTime, restriction, ResourceCategory.OutdoorVenue);
    }
}
