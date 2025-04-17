package com.campus.DataTransferObject.Resource;

import com.campus.Entity.Resource.Venue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VenueDTO extends ResourceDTO{
    public VenueDTO(){}
    public VenueDTO(Integer resourceId,String resourceName,
                    LocalTime openTime, LocalTime closeTime){
        super(resourceId,resourceName, openTime, closeTime);
    }
    public static VenueDTO mapper(Venue venue){
        return new VenueDTO(
                venue.getResourceId(),
                venue.getResourceName(),
                venue.getOpenTime(),
                venue.getCloseTime());
    }
    public static List<VenueDTO> eventListMapper(List<Venue>venues){
        List<VenueDTO> venueDTOS = new ArrayList<>();
        for (Venue venue : venues){
            venueDTOS.add(mapper(venue));
        }
        return venueDTOS;
    }
}
