package com.campus.Entity.Resource;

import com.campus.EntityClassification.ResourceCategory;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class OutdoorVenue extends Resource{
    public OutdoorVenue(){}
    public OutdoorVenue(String resourceName,
                        LocalDateTime openTime, LocalDateTime closeTime){
        super(resourceName, openTime, closeTime, ResourceCategory.OutdoorVenue);
    }
}
