package com.campus.Service.Resource;

import com.campus.Entity.Resource.Venue;
import com.campus.Repository.Resource.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService extends ResourceService{
    @Autowired
    private VenueRepository venueRepository;
    public List<Venue> getAllVenue(){
        return venueRepository.findAll();
    }
}
