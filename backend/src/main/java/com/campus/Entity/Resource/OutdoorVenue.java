package com.campus.Entity.Resource;

import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import jakarta.persistence.Entity;

import java.time.LocalTime;

@Entity
public class OutdoorVenue extends Venue{
    public OutdoorVenue(){}
    public OutdoorVenue(String resourceName,
                        LocalTime openTime, LocalTime closeTime,
                        Restriction restriction,
                        String location){
        super(resourceName, openTime, closeTime, restriction, ResourceCategory.OutdoorVenue);
        setLocation(location);
    }
    private String location;
    public void changeLocation(String location){
        setLocation(location);
    }
    public String getLocation() {
        return location;
    }
    private void setLocation(String location) {
        this.location = location;
    }
    public String toString(){
        return String.format(
                """
                        Category        : %s
                        Name            : %s
                        Location        : %s
                        Open Time       : %s
                        Close Time      : %s
                        """,
                getResourceCategory(),
                getResourceName(),getLocation(),
                getOpenTime(),getCloseTime());
    }
}
