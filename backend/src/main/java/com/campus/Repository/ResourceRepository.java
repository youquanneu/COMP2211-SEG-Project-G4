package com.campus.Repository;

import com.campus.Entity.Reservation;
import com.campus.Entity.Resource;
import com.campus.EntityClassification.ResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource,Integer> {
    List<Resource> findByResourceCategory(ResourceCategory resourceCategory);

}
