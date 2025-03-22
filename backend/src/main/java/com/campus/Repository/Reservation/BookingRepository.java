package com.campus.Repository.Reservation;

import com.campus.Entity.Reservation.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
}
