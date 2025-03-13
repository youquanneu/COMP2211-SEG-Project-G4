package com.campus.Repository.Resource;

import com.campus.Entity.Resource.IndoorVenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndoorVenueRepository extends JpaRepository<IndoorVenue,Integer> {
    List<IndoorVenue> findByBuildingContainingIgnoreCase(String building);
    List<IndoorVenue> findByRoomNumberContainingIgnoreCase(String roomNumber);
    List<IndoorVenue> findByBuildingContainingIgnoreCaseOrRoomNumberContainingIgnoreCase(String building, String roomNumber);
}
