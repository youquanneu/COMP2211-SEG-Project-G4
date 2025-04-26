package com.campus.DataTransferObject.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Venue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VenueDTO extends ResourceDTO{
    public VenueDTO(){}
    public VenueDTO(Integer resourceId,
                    String resourceName,
                    LocalTime openTime,
                    LocalTime closeTime,
                    Restriction restriction){
        super(resourceId,resourceName, openTime, closeTime,restriction);
    }
    public static VenueDTO mapper(Venue venue){
        return new VenueDTO(
                venue.getResourceId(),
                venue.getResourceName(),
                venue.getOpenTime(),
                venue.getCloseTime(),
                venue.getRestriction());
    }
    public static List<VenueDTO> venueListMapper(List<Venue>venues){
        List<VenueDTO> venueDTOS = new ArrayList<>();
        for (Venue venue : venues){
            venueDTOS.add(mapper(venue));
        }
        return venueDTOS;
    }
}
