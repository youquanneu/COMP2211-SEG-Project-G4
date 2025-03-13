package com.campus.Service.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Equipment;
import com.campus.Repository.Resource.EquipmentRepository;
import com.campus.Repository.Resource.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class EquipmentService extends ResourceService{
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private ResourceRepository resourceRepository;
    public void addEquipment(){
        Equipment equipment = new Equipment("S15","Computer23", LocalTime.now(), LocalTime.now(), Restriction.ApprovalRequired);
        saveEquipment(equipment);
    }
    private Equipment saveEquipment(Equipment equipment){
        return equipmentRepository.save(equipment);
    }   // Insert a new equipment into database
    private void deleteEquipment(Equipment equipment){
        equipmentRepository.delete(equipment);
    }
    private void modifySerialNumber(Equipment equipment, String serialNumber){
        equipment.changeSerialNumber(serialNumber);
        saveEquipment(equipment);
    }
}
