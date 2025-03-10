package com.campus.Repository;

import com.campus.Entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource,Integer> {
    List<Resource> getAllResource();
}
