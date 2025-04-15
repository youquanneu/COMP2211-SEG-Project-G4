package com.campus.Repository.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Resource;
import com.campus.Classification.ResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query("select resource from Resource resource " +
            "where  (:resourceId        is null or resource.resourceId  = :resourceId)  " +
            "and    (:resourceName      is null or upper(resource.resourceName) like concat('%',upper(:resourceName),'%'))" +
            "and    (:openTime          is null or resource.openTime    < :openTime)    " +
            "and    (:closeTime         is null or resource.closeTime   > :closeTime)   " +
            "and    (:restriction       is null or resource.restriction = :restriction) " +
            "and    (:resourceCategory  is null or resource.resourceCategory = :resourceCategory) ")
    List<Resource> findResourceByFilter(@Param("resourceId")        Integer resourceId,
                                        @Param("resourceName")      String resourceName,
                                        @Param("openTime")          LocalTime openTime,
                                        @Param("closeTime")         LocalTime closeTime,
                                        @Param("restriction")       Restriction restriction,
                                        @Param("resourceCategory")  ResourceCategory resourceCategory);


}
