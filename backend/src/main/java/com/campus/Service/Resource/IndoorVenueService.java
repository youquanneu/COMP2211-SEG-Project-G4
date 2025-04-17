package com.campus.Service.Resource;

import com.campus.Entity.Resource.IndoorVenue;
import com.campus.Repository.Resource.IndoorVenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IndoorVenueService extends VenueService{
    private IndoorVenueRepository indoorVenueRepository;
    public IndoorVenue getIndoorVenueByBuildingAndRoomNumber(String building, String roomNumber){
        Optional<IndoorVenue> indoorVenue = indoorVenueRepository.findIndoorVenueByBuildingEqualsIgnoreCaseAndRoomNumberEqualsIgnoreCase(building, roomNumber);
        if (indoorVenue.isEmpty()){
            throw new RuntimeException("Indoor Venue not found");
        }
        return indoorVenue.get();
    }
    public List<IndoorVenue> getAllIndoorVenue(){
        return indoorVenueRepository.findAll();
    }
    public List<IndoorVenue> getIndoorVenueByBuilding(String building){
        return indoorVenueRepository.findByBuildingEqualsIgnoreCase(building);
    }
    public List<IndoorVenue> getIndoorVenueByRoomNumber(String roomNumber){
        return indoorVenueRepository.findByRoomNumberEqualsIgnoreCase(roomNumber);
    }
    public List<IndoorVenue> getIndoorVenueBySearching(String search){
        return indoorVenueRepository.findByBuildingContainingIgnoreCaseOrRoomNumberContainingIgnoreCase(search,search);
    }
}
