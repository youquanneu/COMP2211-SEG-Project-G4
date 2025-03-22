package com.campus.DataTransferObject.Resource;

import java.time.LocalTime;

public class VenueDTO extends ResourceDTO{
    public VenueDTO(){}
    public VenueDTO(Integer resourceId,String resourceName,
                    LocalTime openTime, LocalTime closeTime){
        super(resourceId,resourceName, openTime, closeTime);
    }
}
