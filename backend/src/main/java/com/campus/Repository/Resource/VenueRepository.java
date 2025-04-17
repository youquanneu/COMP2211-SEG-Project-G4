package com.campus.Repository.Resource;

import com.campus.Entity.Resource.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue,Integer> {
}
