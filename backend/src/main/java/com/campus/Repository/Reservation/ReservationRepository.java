package com.campus.Repository.Reservation;

import com.campus.Classification.Status;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    List<Reservation> findReservationByBooker(User booker);
    List<Reservation> findReservationByResource(Resource resource);
    List<Reservation> findReservationByStatus(Status status);
    // Value of status : 0 = approved, 1 = pending, 2 = rejected, 3 = cancelled
    @Query("select reservation from Reservation reservation " +
            "where  (reservation.resource  = :resource)  " +
            "and    (   (reservation.reservationStarting <= :reservationStarting and reservation.reservationEnding >= :reservationStarting) " +
            "        or (reservation.reservationStarting <= :reservationEnding   and reservation.reservationEnding >= :reservationEnding )) " +
            "and    (reservation.status = 0 or reservation.status = 1)" // Ignore rejected and cancelled reservation
    )
    List<Reservation> filterConflictReservation(
            @Param("resource")              Resource resource,
            @Param("reservationStarting")   LocalDateTime reservationStarting,
            @Param("reservationEnding")     LocalDateTime reservationEnding);

    @Query("select reservation from Reservation reservation " +
            "where  (reservation.reservationId != :reservationId) " +
            "and    (reservation.resource       = :resource)  " +
            "and    (   (reservation.reservationStarting <= :reservationStarting and reservation.reservationEnding >= :reservationStarting) " +
            "        or (reservation.reservationStarting <= :reservationEnding   and reservation.reservationEnding >= :reservationEnding )) " +
            "and    (reservation.status = 0 or reservation.status = 1)" // Ignore rejected and cancelled reservation
    )
    List<Reservation> filterOtherConflictReservation(
            @Param("reservationId")         Integer reservationId,
            @Param("resource")              Resource resource,
            @Param("reservationStarting")   LocalDateTime reservationStarting,
            @Param("reservationEnding")     LocalDateTime reservationEnding);
    @Query("select reservation from Reservation reservation " +
            "where  (:reservationId         is null or reservation.reservationId        = :reservationId) " +
            "and    (:booker                is null or reservation.booker               = :booker)" +
            "and    (:resource              is null or reservation.resource             = :resource)  " +
            "and    ((:reservationAfter     is null or reservation.reservationStarting  >= :reservationAfter)" +
            "       and (:reservationBefore is null or reservation.reservationEnding    <= :reservationBefore)) " +
            "and    (:status is null or reservation.status = :status)" // Ignore rejected and cancelled reservation
    )
    List<Reservation> filterReservation(
            @Param("reservationId")     Integer reservationId,
            @Param("booker")            User booker,
            @Param("resource")          Resource resource,
            @Param("reservationAfter")  LocalDateTime reservationAfter,
            @Param("reservationBefore") LocalDateTime reservationBefore,
            @Param("status") Status status);
    @Query("select reservation from Reservation reservation " +
            "where    (reservation.resource             = :resource)  " +
            "and    ((reservation.reservationStarting  >= :dateStart)" +
            "       and (reservation.reservationEnding    <= :dateEnd)) " +
            "and    (reservation.status = 0 or reservation.status = 1)" // Ignore rejected and cancelled reservation
    )
    List<Reservation> filterReservationByDate(
            @Param("resource")          Resource resource,
            @Param("dateStart")  LocalDateTime dateStart,
            @Param("dateEnd")  LocalDateTime dateEnd);
}
