package com.campus.Entity.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Classification.ResourceCategory;
import jakarta.persistence.*;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Resource {
    public Resource(){}
    public Resource(String resourceName,
                    LocalTime openTime, LocalTime closeTime,
                    Restriction restriction,
                    ResourceCategory resourceCategory){
        setResourceName(resourceName);
        checkOpenAndCloseTime(openTime,closeTime);
        setOpenTime(openTime);
        setCloseTime(closeTime);
        setRestriction(restriction);
        setResourceCategory(resourceCategory);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resourceId;
    @NotNull
    private String resourceName;
    private LocalTime openTime;
    private LocalTime closeTime;
    @NotNull
    private Restriction restriction;
    private ResourceCategory resourceCategory;
    @OneToMany
    private List<Reservation> booking;
    public void changeResourceName(String resourceName){
        setResourceName(resourceName);
    }
    public void changeOpenTime(LocalTime openTime){
        setOpenTime(openTime);
    }
    public void changeCloseTime(LocalTime closeTime){
        setCloseTime(closeTime);
    }
    public void changeRestriction(Restriction restriction){
        setRestriction(restriction);
    }
    public Integer getResourceId() {
        return resourceId;
    }
    public String getResourceName() {
        return resourceName;
    }
    public LocalTime getOpenTime() {
        return openTime;
    }
    public LocalTime getCloseTime() {
        return closeTime;
    }
    public Restriction getRestriction() {
        return restriction;
    }
    public ResourceCategory getResourceCategory() {
        return resourceCategory;
    }
    private void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    private void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }
    private void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }
    private void setRestriction(Restriction restriction) {
        this.restriction = restriction;
    }
    private void setResourceCategory(ResourceCategory resourceCategory) {
        this.resourceCategory = resourceCategory;
    }
    private void checkOpenAndCloseTime(LocalTime openTime, LocalTime closeTime){
        if ((openTime == null || closeTime == null)&&(openTime != closeTime)){
            throw new RuntimeException("Open Time and Close Time should be either both null or both not null");
        }
        else if (openTime!=null && openTime.isAfter(closeTime)){
            throw new RuntimeException("Close Time should be after Open Time");
        }
    }
}