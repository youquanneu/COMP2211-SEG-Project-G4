package com.campus.Repository.Resource;

import com.campus.Entity.Resource.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,Integer> {
    Optional<Equipment> findBySerialNumberEqualsIgnoreCase(String serialNumber);
    List<Equipment> findBySerialNumberContainingIgnoreCase(String serialNumber);
}
