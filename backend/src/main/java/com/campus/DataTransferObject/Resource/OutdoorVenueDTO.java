package com.campus.DataTransferObject.Resource;

import com.campus.Classification.Restriction;

import java.time.LocalTime;

public class OutdoorVenueDTO extends VenueDTO{
    public OutdoorVenueDTO(){}
    public OutdoorVenueDTO(Integer resourceId,
                           String resourceName,
                           LocalTime openTime,
                           LocalTime closeTime,
                           Restriction restriction,
                           String location){
        super(resourceId,resourceName, openTime, closeTime,restriction);
        setLocation(location);
    }
    private String location;
    public String getLocation() {
        return location;
    }
    private void setLocation(String location) {
        this.location = location;
    }
}
