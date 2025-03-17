package com.campus.DataTransferObject.Resource;

import java.time.LocalTime;

public class VenueDTO extends ResourceDTO{
    public VenueDTO(){}
    public VenueDTO(String resourceName,
                    LocalTime openTime, LocalTime closeTime){
        super(resourceName, openTime, closeTime);
    }
}
