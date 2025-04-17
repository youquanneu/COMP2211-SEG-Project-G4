package com.campus.Repository.Resource;

import com.campus.Entity.Resource.OutdoorVenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutdoorVenueRepository extends JpaRepository<OutdoorVenue,Integer> {
}
