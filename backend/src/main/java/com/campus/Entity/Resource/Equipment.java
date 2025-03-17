package com.campus.Entity.Resource;

import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

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
    @NotNull
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
}
