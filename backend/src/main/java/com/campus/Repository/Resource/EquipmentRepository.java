package com.campus.Repository.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,Integer> {
    Optional<Equipment> findBySerialNumberEqualsIgnoreCase(String serialNumber);
    List<Equipment> findBySerialNumberContainingIgnoreCase(String serialNumber);
    @Query("select equipment from Equipment equipment " +
            "where  (:resoureId     is null or equipment.resourceId     = :resourceId)"+
            "and    (:resourceName  is null or upper(equipment.resourceName)    like concat('%', upper(:resourceName),'%' ))"+
            "and    (:openTime      is null or equipment.openTime       < :openTime)    " +
            "and    (:closeTime     is null or equipment.closeTime      > :closeTime)   " +
            "and    (:restriction   is null or equipment.restriction    = :restriction) " +
            "and    (:serialNumber  is null or upper(equipment.serialNumber)    like concat('%',upper(:serialNumber),'%'))"
    )
    List<Equipment> findEquipmentByFilter(@Param("resourceId") Integer resourceId,
                                          @Param("resourceName") String resourceName,
                                          @Param("openTime")LocalTime openTime,
                                          @Param("closeTime") LocalTime closeTime,
                                          @Param("restriction")Restriction restriction,
                                          @Param("serialNumber") String serialNumber);
}
