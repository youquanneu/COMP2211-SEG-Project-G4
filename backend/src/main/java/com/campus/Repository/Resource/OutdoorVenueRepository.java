package com.campus.Repository.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.OutdoorVenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface OutdoorVenueRepository extends JpaRepository<OutdoorVenue,Integer> {
    List<OutdoorVenue> findOutdoorVenueByLocationContainingIgnoreCase(String location);
    @Query("select outdoorVenue from OutdoorVenue outdoorVenue " +
            "where  (:resourceId        is null or outdoorVenue.resourceId  = :resourceId)  " +
            "and    (:resourceName      is null or upper(outdoorVenue.resourceName) like concat('%',upper(:resourceName),'%'))" +
            "and    (:openTime          is null or outdoorVenue.openTime    < :openTime)    " +
            "and    (:closeTime         is null or outdoorVenue.closeTime   > :closeTime)   " +
            "and    (:restriction       is null or outdoorVenue.restriction = :restriction) " +
            "and    (:location          is null or upper(outdoorVenue.location)     like concat('%',upper(:location),'%'))")
    List<OutdoorVenue> findOutdoorVenueByFilter(@Param("resourceId") Integer resourceId,
                                                @Param("resourceName") String resourceName,
                                                @Param("openTime") LocalTime openTime,
                                                @Param("closeTime") LocalTime closeTime,
                                                @Param("restriction") Restriction restriction,
                                                @Param("location") String location);
}
