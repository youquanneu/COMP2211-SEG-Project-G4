package com.campus.Service.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Equipment;
import com.campus.Repository.Resource.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService extends ResourceService{
    @Autowired
    private EquipmentRepository equipmentRepository;
    public Equipment getEquipmentById(Integer id){
        return (Equipment) getResourceByID(id);
    }
    public Equipment getEquipmentBySerialNumber(String serialNumber){
        Optional<Equipment> equipment = equipmentRepository.findBySerialNumberEqualsIgnoreCase(serialNumber);
        if (equipment.isEmpty()){
            throw new RuntimeException("Equipment not found");
        }
        return equipment.get();
    }
    public List<Equipment> filterEquipment(Integer resourceId, String resourceName,
                                           LocalTime openTime, LocalTime closeTime,
                                           Restriction restriction, String serialNumber){
        return equipmentRepository.findEquipmentByFilter
                (resourceId,resourceName,openTime,closeTime,restriction,serialNumber);
    }   // Base Function : Filter equipment
    public List<Equipment> searchEquipmentBySerialNumber(String serialNumber){
        return equipmentRepository.findBySerialNumberContainingIgnoreCase(serialNumber);
    }
}
