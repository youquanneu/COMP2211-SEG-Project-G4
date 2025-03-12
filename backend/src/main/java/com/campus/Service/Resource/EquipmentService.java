package com.campus.Service.Resource;

import com.campus.Entity.Resource.Equipment;
import com.campus.Entity.User.User;
import com.campus.Repository.Resource.EquipmentRepository;
import com.campus.Repository.Resource.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private ResourceRepository resourceRepository;
    public void addEquipment(){
        Equipment equipment = new Equipment("S15","Computer23", LocalDateTime.now(),LocalDateTime.now());
        saveEquipment(equipment);
    }
    private Equipment saveEquipment(Equipment equipment){
        return equipmentRepository.save(equipment);
    }   // Insert a new user into database
}
