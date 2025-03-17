package com.campus.Repository.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Resource;
import com.campus.Classification.ResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource,Integer> {
    Optional<Resource> findByResourceNameEqualsIgnoreCase(String resourceName);
    List<Resource> findByRestriction(Restriction restriction);
    List<Resource> findByResourceCategory(ResourceCategory resourceCategory);
    List<Resource> findByResourceNameContainingIgnoreCase(String resourceName);
    List<Resource> findByOpenTimeBeforeAndCloseTimeAfter(LocalTime openTime, LocalTime closeTime);
}
