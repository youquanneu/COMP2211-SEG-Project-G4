package com.campus.Repository.Reservation;

import com.campus.Classification.Approval;
import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    @Query("select reservation from Reservation reservation " +
            "where  (reservation.resource  = :resource)  " +
            "and    (   " +
            "           (reservation.reservationStarting < :reservationStarting and reservation.reservationEnding > :reservationStarting) " +
            "           or " +
            "           (reservation.reservationStarting < :reservationEnding and reservation.reservationEnding > :reservationEnding )" +
            "       ) " +
            "and    (reservation.approval != :approval)"
    )
    List<Reservation> findConflictReservation(
            @Param("resource")              Resource resource,
            @Param("reservationStarting")   LocalDateTime reservationStarting,
            @Param("reservationEnding")     LocalDateTime reservationEnding,
            @Param("approval")              Approval approval);
}
