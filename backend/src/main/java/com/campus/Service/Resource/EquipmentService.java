package com.campus.Service.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Equipment;
import com.campus.Repository.Resource.EquipmentRepository;
import com.campus.Repository.Resource.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Scanner;

@Service
public class EquipmentService extends ResourceService{
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private ResourceRepository resourceRepository;
    public void addEquipment(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Serial Number : ");
        String serialNumber = scanner.nextLine();
        System.out.println("Equipment Name : ");
        String name = scanner.nextLine();
        Equipment equipment =
        saveEquipment(new Equipment(serialNumber,name, LocalTime.now(), LocalTime.now(), Restriction.ApprovalRequired));
    }
    public Equipment saveEquipment(Equipment equipment){
        return equipmentRepository.save(equipment);
    }   // Insert a new equipment into database
    private void deleteEquipment(Equipment equipment){
        equipmentRepository.delete(equipment);
    }
    public void modifySerialNumber(Equipment equipment, String serialNumber){
        equipment.changeSerialNumber(serialNumber);
        saveEquipment(equipment);
    }
}
