package com.campus.Repository.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.Resource.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface VenueRepository extends JpaRepository<Venue,Integer> {
    Optional<Venue> findByResourceNameEqualsIgnoreCase(String resourceName);
    List<Venue> findByResourceNameContainingIgnoreCase(String resourceName);
    @Query("select venue from Venue venue " +
            "where  (:resourceId        is null or venue.resourceId   = :resourceId)  " +
            "and    (:resourceName      is null or upper(venue.resourceName)  like concat('%',upper(:resourceName),'%'))" +
            "and    (:openTime          is null or venue.openTime     < :openTime)    " +
            "and    (:closeTime         is null or venue.closeTime    > :closeTime)   " +
            "and    (:restriction       is null or venue.restriction  = :restriction) "
    )
    List<Venue> findVenueByFilter(@Param("resourceId")      Integer resourceId,
                                  @Param("resourceName")    String resourceName,
                                  @Param("openTime") LocalTime openTime,
                                  @Param("closeTime")       LocalTime closeTime,
                                  @Param("restriction") Restriction restriction);
}
