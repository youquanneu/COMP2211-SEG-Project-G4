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
    @Query("select reservation from Reservation reservation " +
            "where  (reservation.resource  = :resource)  " +
            "and    (   " +
            "           (reservation.reservationStarting < :reservationStarting and reservation.reservationEnding > :reservationStarting) " +
            "           or " +
            "           (reservation.reservationStarting < :reservationEnding and reservation.reservationEnding > :reservationEnding )" +
            "       ) " +
            "and    (reservation.status != :status)"
    )
    List<Reservation> filterConflictReservation(
            @Param("resource")              Resource resource,
            @Param("reservationStarting")   LocalDateTime reservationStarting,
            @Param("reservationEnding")     LocalDateTime reservationEnding,
            @Param("status") Status status);
    @Query("select reservation from Reservation reservation " +
            "where  (reservation.resource  = :resource)  " +
            "and    (   " +
            "           (reservation.reservationStarting < :reservationStarting and reservation.reservationEnding > :reservationStarting) " +
            "           or " +
            "           (reservation.reservationStarting < :reservationEnding and reservation.reservationEnding > :reservationEnding )" +
            "       ) " +
            "and    (reservation.status != :approval)"
    )
    List<Reservation> filterReservationByResourceAndPeriod(
            @Param("resource")              Resource resource,
            @Param("reservationStarting")   LocalDateTime reservationStarting,
            @Param("reservationEnding")     LocalDateTime reservationEnding,
            @Param("status") Status status);
}
