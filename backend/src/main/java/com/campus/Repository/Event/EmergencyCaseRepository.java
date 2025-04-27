package com.campus.Repository.Event;

import com.campus.Classification.Status;
import com.campus.Entity.Event.EmergencyCase;
import com.campus.Entity.Resource.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmergencyCaseRepository extends JpaRepository<EmergencyCase,Integer> {
    Optional<EmergencyCase> findFirstByOrderByEmergencyCaseIdDesc();
    @Query("select emergencyCase from EmergencyCase emergencyCase "+
            "where  (:emergencyCaseId   is null or emergencyCase.emergencyCaseId    = :emergencyCaseId)  "+
            "and    (:location          is null or emergencyCase.location           = :location)  "+
            "and    (:emergencyCase           is null or upper(emergencyCase.emergencyCase)     like concat('%',upper(:emergencyCase),'%'))"+
            "and    (:description           is null or upper(emergencyCase.description)     like concat('%',upper(:description),'%'))"+
            "and    (:reporterEmail          is null or upper(emergencyCase.reporterEmail)    like concat('%',upper(:reporterEmail),'%'))"+
            "and    (:status          is null or emergencyCase.status   = :status)"+
            "and    (:timeAfter         is null or emergencyCase.reportedTime       >= :timeAfter) " +
            "and    (:timeBefore        is null or emergencyCase.reportedTime       <= :timeBefore)"
    )
    List<EmergencyCase> findEmergencyCaseByFilter(@Param("emergencyCaseId") Integer emergencyCaseId ,
                                                  @Param("location")        Venue location          ,
                                                  @Param("emergencyCase")         String emergencyCase ,
                                                  @Param("description")         String description    ,
                                                  @Param("reporterEmail")        String reporterEmail  ,
                                                  @Param("status") Status status,
                                                  @Param("timeAfter")       LocalDateTime timeAfter ,
                                                  @Param("timeBefore")      LocalDateTime timeBefore);

}
