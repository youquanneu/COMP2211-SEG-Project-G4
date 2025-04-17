package com.campus.DataTransferObject.Resource;

import java.time.LocalTime;

public class OutdoorVenueDTO extends VenueDTO{
    public OutdoorVenueDTO(){}
    public OutdoorVenueDTO(String resourceName,
                           LocalTime openTime, LocalTime closeTime,
                           String location){
        super(resourceName, openTime, closeTime);
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
