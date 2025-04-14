package com.campus.Repository.Event;

import com.campus.Classification.UserRole;
import com.campus.Entity.Event.EmergencyCase;
import com.campus.Entity.Resource.Venue;
import com.campus.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EmergencyCaseRepository extends JpaRepository<EmergencyCase,Integer> {
    @Query("select emergencyCase from EmergencyCase emergencyCase "+
            "where  (:emergencyCaseId    is null or emergencyCase.emergencyCaseId      = :emergencyCaseId)  "+
            "where  (:location    is null or emergencyCase.location      = :location)  "+
            "and    (:content  is null or upper(emergencyCase.content) like concat('%',upper(:content),'%'))"+
            "and    (:reporter     is null or upper(emergencyCase.reporter)    like concat('%',upper(:reporter),'%'))"+
            "and    (:timeAfter  is null or emergencyCase.reportedTime    >= :timeAfter) " +
            "and    (:timeBefore  is null or emergencyCase.reportedTime    <= :timeBefore)"
    )
    List<User> findUserByFilter(@Param("emergencyCaseId")    Integer emergencyCaseId  ,
                                @Param("location") Venue location ,
                                @Param("content")   String content    ,
                                @Param("username")  String reporter ,
                                @Param("timeAfter") LocalDateTime timeAfter ,
                                @Param("timeBefore") LocalDateTime timeBefore);
}
