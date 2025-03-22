package com.campus.Repository.Reservation;

import com.campus.Classification.Approval;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    List<Reservation> findReservationByResourcesAndApprovalIsNot(Resource resources, Approval approval);
}
