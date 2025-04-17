package com.campus.Service.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Venue;
import com.campus.Repository.Resource.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class VenueService extends ResourceService{
    @Autowired
    private VenueRepository venueRepository;
    public List<Venue> getAllVenue(){
        return venueRepository.findAll();
    }
    public List<Venue> filterVenue(Integer resourceId, String resourceName,
                                   LocalTime openTime, LocalTime closeTime,
                                   Restriction restriction){
        return venueRepository.findVenueByFilter(resourceId,resourceName,openTime,closeTime,restriction);
    }
}
