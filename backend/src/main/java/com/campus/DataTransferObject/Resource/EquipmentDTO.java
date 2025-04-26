package com.campus.DataTransferObject.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Equipment;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDTO extends ResourceDTO{
    public EquipmentDTO(){}
    public EquipmentDTO(Integer resourceId,
                        String resourceName,
                        LocalTime openTime,
                        LocalTime closeTime,
                        Restriction restriction,
                        String serialNumber){
        super(resourceId,resourceName, openTime, closeTime,restriction);
        setSerialNumber(serialNumber);
    }
    @JsonProperty
    private String serialNumber;
    public String getSerialNumber() {
        return serialNumber;
    }
    private void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    public static EquipmentDTO mapper(Equipment equipment){
        return new EquipmentDTO(
                equipment.getResourceId(),
                equipment.getResourceName(),
                equipment.getOpenTime(),
                equipment.getCloseTime(),
                equipment.getRestriction(),
                equipment.getSerialNumber());
    }
    public static List<EquipmentDTO> equipmentListMapper(List<Equipment> equipments){
        List<EquipmentDTO> equipmentDTOS = new ArrayList<>();
        for (Equipment equipment : equipments){
            equipmentDTOS.add(mapper(equipment));
        }
        return equipmentDTOS;
    }
}
