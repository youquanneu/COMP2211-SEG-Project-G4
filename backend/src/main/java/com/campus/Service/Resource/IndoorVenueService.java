package com.campus.Service.Resource;

import com.campus.Entity.Resource.IndoorVenue;
import com.campus.Repository.Resource.IndoorVenueRepository;
import org.springframework.stereotype.Service;

@Service
public class IndoorVenueService extends ResourceService{
    private IndoorVenueRepository indoorVenueRepository;
    private IndoorVenue saveIndoorVenue(IndoorVenue indoorVenue){
        return indoorVenueRepository.save(indoorVenue);
    }
    private void deleteIndoorVenue(IndoorVenue indoorVenue){
        indoorVenueRepository.delete(indoorVenue);
    }
    private void modifyBuilding(IndoorVenue indoorVenue, String building){
        indoorVenue.changeBuilding(building);
        saveIndoorVenue(indoorVenue);
    }
    private void modifyRoomNumber(IndoorVenue indoorVenue, String roomNumber){
        indoorVenue.changeRoomNumber(roomNumber);
        saveIndoorVenue(indoorVenue);
    }
}
