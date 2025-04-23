package com.campus.DataTransferObject.Resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public class EquipmentDTO extends ResourceDTO{
    public EquipmentDTO(){}
    public EquipmentDTO(Integer resourceId, String resourceName,
                        LocalTime openTime, LocalTime closeTime,
                        String serialNumber){
        super(resourceId,resourceName, openTime, closeTime);
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
}
