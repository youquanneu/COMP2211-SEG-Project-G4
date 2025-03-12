package com.campus.Entity.Resource;

import com.campus.EntityClassification.ResourceCategory;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Equipment extends Resource{
    public Equipment() {}
    public Equipment(String serialNumber, String resourceName,
                     LocalDateTime openTime, LocalDateTime closeTime){
        super(resourceName, openTime, closeTime, ResourceCategory.Equipment);
        setSerialNumber(serialNumber);
    }
    private String serialNumber;
    public String getSerialNumber() {
        return serialNumber;
    }
    private void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
