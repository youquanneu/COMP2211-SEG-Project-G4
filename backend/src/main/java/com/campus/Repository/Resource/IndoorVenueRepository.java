package com.campus.Repository.Resource;

import com.campus.Entity.Resource.IndoorVenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndoorVenueRepository extends JpaRepository<IndoorVenue,Integer> {
    Optional<IndoorVenue> findIndoorVenueByBuildingEqualsIgnoreCaseAndRoomNumberEqualsIgnoreCase(String building, String roomNumber);
    List<IndoorVenue> findByBuildingEqualsIgnoreCase(String building);
    List<IndoorVenue> findByRoomNumberEqualsIgnoreCase(String roomNumber);
    List<IndoorVenue> findByBuildingContainingIgnoreCaseOrRoomNumberContainingIgnoreCase(String building, String roomNumber);
}
