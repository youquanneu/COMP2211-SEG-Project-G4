package com.campus.Repository.Reservation;

import com.campus.Entity.Reservation.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot,Integer> {
}
