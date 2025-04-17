package com.campus.Service.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.OutdoorVenue;
import com.campus.Repository.Resource.OutdoorVenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class OutdoorVenueService extends VenueService{
    @Autowired
    private OutdoorVenueRepository outdoorVenueRepository;
    public OutdoorVenue getOutdoorVenueById(Integer id){
        return (OutdoorVenue) getResourceByID(id);
    }
    public List<OutdoorVenue> searchOutdoorVenuesByLocation(String location){
        return outdoorVenueRepository.findOutdoorVenueByLocationContainingIgnoreCase(location);
    }
    public List<OutdoorVenue> getAllOutdoorVenue(){
        return outdoorVenueRepository.findAll();
    }
    public List<OutdoorVenue> filterOutdoorVenue(Integer resourceId, String resourceName,
                                                 LocalTime openTime, LocalTime closeTime,
                                                 Restriction restriction, String location){
        return outdoorVenueRepository.findOutdoorVenueByFilter
                (resourceId,resourceName,openTime,closeTime,restriction,location);
    }
}
