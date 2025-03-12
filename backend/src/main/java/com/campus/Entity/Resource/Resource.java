package com.campus.Entity.Resource;

import com.campus.Entity.Reservation;
import com.campus.EntityClassification.ResourceCategory;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Resource {
    public Resource(){}
    public Resource(String resourceName,
                    LocalDateTime openTime, LocalDateTime closeTime,
                    ResourceCategory resourceCategory){
        setResourceName(resourceName);
        setOpenTime(openTime);
        setCloseTime(closeTime);
        setResourceCategory(resourceCategory);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resourceId;
    private String resourceName;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    private ResourceCategory resourceCategory;
    public Integer getResourceId() {
        return resourceId;
    }
    public LocalDateTime getOpenTime() {
        return openTime;
    }
    public String getResourceName() {
        return resourceName;
    }
    public LocalDateTime getCloseTime() {
        return closeTime;
    }
    public ResourceCategory getResourceCategory() {
        return resourceCategory;
    }
    private void setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
    }
    private void setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
    }
    private void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    private void setResourceCategory(ResourceCategory resourceCategory) {
        this.resourceCategory = resourceCategory;
    }
    @ManyToOne
    @JoinColumn(name = "reservationId",nullable = true)
    private Reservation booking;
}

