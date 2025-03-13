package com.campus.Repository.Resource;

import com.campus.Entity.Resource.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,Integer> {
    List<Equipment> findBySerialNumberContainingIgnoreCase(String serialNumber);
}
