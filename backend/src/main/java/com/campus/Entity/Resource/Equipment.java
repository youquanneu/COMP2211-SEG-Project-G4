package com.campus.Entity.Resource;

import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
@Data
@Entity
public class Equipment extends Resource{
    public Equipment() {}
    public Equipment(String resourceName,
                     LocalTime openTime, LocalTime closeTime,
                     Restriction restriction,
                     String serialNumber){
        super(resourceName, openTime, closeTime, restriction, ResourceCategory.Equipment);
        setSerialNumber(serialNumber);
    }
    @Column(unique = true)
    private String serialNumber;
    public void changeSerialNumber(String serialNumber){
        setSerialNumber(serialNumber);
    }
    public String getSerialNumber() {
        return serialNumber;
    }
    private void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    public String toString(){
        return String.format(
                """
                        Category        : %s
                        Name            : %s
                        Serial Number   : %s
                        Open Time       : %s
                        Close Time      : %s
                        """,
                getResourceCategory(),
                getResourceName(),
                getSerialNumber(),
                getOpenTime(),
                getCloseTime());
    }
}
