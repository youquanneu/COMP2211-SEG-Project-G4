package com.campus.Repository.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.IndoorVenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IndoorVenueRepository extends JpaRepository<IndoorVenue,Integer> {
    Optional<IndoorVenue> findIndoorVenueByBuildingEqualsIgnoreCaseAndRoomNumberEqualsIgnoreCase(String building, String roomNumber);
    List<IndoorVenue> findByRestriction(Restriction restriction);
    List<IndoorVenue> findByBuildingEqualsIgnoreCase(String building);
    List<IndoorVenue> findByRoomNumberEqualsIgnoreCase(String roomNumber);
    List<IndoorVenue> findByBuildingContainingIgnoreCaseOrRoomNumberContainingIgnoreCase(String building, String roomNumber);
    @Query("select indoorVenue from IndoorVenue indoorVenue " +
            "where  (:resourceId        is null or indoorVenue.resourceId   = :resourceId)  " +
            "and    (:resourceName      is null or upper(indoorVenue.resourceName)  like concat('%',upper(:resourceName),'%'))" +
            "and    (:openTime          is null or indoorVenue.openTime     < :openTime)    " +
            "and    (:closeTime         is null or indoorVenue.closeTime    > :closeTime)   " +
            "and    (:restriction       is null or indoorVenue.restriction  = :restriction) " +
            "and    (:building          is null or upper(indoorVenue.building)      like concat('%',upper(:building),'%'))" +
            "and    (:building          is null or upper(indoorVenue.roomNumber)    like concat('%',upper(:roomNumber),'%'))"
    )
    List<IndoorVenue> findIndoorVenueByFilter(@Param("resourceId")      Integer resourceId,
                                              @Param("resourceName")    String resourceName,
                                              @Param("openTime")        LocalTime openTime,
                                              @Param("closeTime")       LocalTime closeTime,
                                              @Param("restriction")     Restriction restriction,
                                              @Param("building")        String location,
                                              @Param("roomNumber")      String roomNumber);
}
