package com.campus.Repository.Resource;

import com.campus.Entity.Resource.Resource;
import com.campus.EntityClassification.ResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource,Integer> {
    List<Resource> findByResourceCategory(ResourceCategory resourceCategory);
}
