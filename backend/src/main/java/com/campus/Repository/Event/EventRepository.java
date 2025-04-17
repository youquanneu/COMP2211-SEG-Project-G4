package com.campus.Repository.Event;

import com.campus.Entity.Event.Event;
import com.campus.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {
    List<Event> findEventsByOrganizerContains(User organizer);
    List<Event> findEventsByParticipantContaining(User participant);
    @Query("select event from Event event "+
            "where  (:eventId           is null or event.eventId            = :eventId)  "+
            "and    (:eventTitle        is null or upper(event.eventTitle) like concat('%',upper(:eventTitle),'%'))"+
            "and    ((:eventStarting    is null or event.eventStarting      >= :eventStarting)" +
            "       and (:eventEnding   is null or event.eventEnding        <= :eventEnding)) " +
            "and    (:eventDescription  is null or upper(event.eventDescription) like concat('%',upper(:eventDescription),'%'))"
    )
    List<Event> findEventByFilter(@Param("eventId")    Integer eventId  ,
                                  @Param("eventTitle")  String eventTitle ,
                                  @Param("eventStarting")    LocalDateTime eventStarting    ,
                                  @Param("eventEnding")LocalDateTime eventEnding,
                                  @Param("eventDescription") String eventDescription);
}
