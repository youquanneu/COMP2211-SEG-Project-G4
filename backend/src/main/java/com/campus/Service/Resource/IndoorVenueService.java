package com.campus.Service.Resource;

import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.IndoorVenue;
import com.campus.Repository.Resource.IndoorVenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IndoorVenueService extends VenueService{
    @Autowired
    private IndoorVenueRepository indoorVenueRepository;
    public IndoorVenue getIndoorVenueById(Integer id){
        return (IndoorVenue) getResourceByID(id);
    }
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
    public List<IndoorVenue> getIndoorVenueByRestriction(Restriction restriction){
        return indoorVenueRepository.findByRestriction(restriction);
    }
    public List<IndoorVenue> getIndoorVenueByBuilding(String building){
        return indoorVenueRepository.findByBuildingEqualsIgnoreCase(building);
    }
    public List<IndoorVenue> getIndoorVenueByRoomNumber(String roomNumber){
        return indoorVenueRepository.findByRoomNumberEqualsIgnoreCase(roomNumber);
    }
    public List<IndoorVenue> searchIndoorVenueByBuildingOrRoomNumber(String buildingOrRoomNumber){
        return indoorVenueRepository.findByBuildingContainingIgnoreCaseOrRoomNumberContainingIgnoreCase(buildingOrRoomNumber,buildingOrRoomNumber);
    }
}
