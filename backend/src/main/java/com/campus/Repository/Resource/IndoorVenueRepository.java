package com.campus.Repository.Resource;

import com.campus.Entity.Resource.IndoorVenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndoorVenueRepository extends JpaRepository<IndoorVenue,Integer> {
}
