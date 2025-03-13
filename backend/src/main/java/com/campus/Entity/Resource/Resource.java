package com.campus.Entity.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Reservation;
import com.campus.Classification.ResourceCategory;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

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
        setOpenTime(openTime);
        setCloseTime(closeTime);
        setRestriction(restriction);
        setResourceCategory(resourceCategory);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resourceId;
    private String resourceName;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Restriction restriction;
    private ResourceCategory resourceCategory;
    public void changeResourceName(String resourceName){
        setResourceName(resourceName);
    }
    public void changeOpenTime(LocalTime openTime){
        setOpenTime(openTime);
    }
    public void changeCloseTime(LocalTime closeTime){
        setCloseTime(closeTime);
    }
    public void setToNonRestriction(){
        setRestriction(Restriction.NonRestriction);
    }
    public void setToApprovalRequired(){
        setRestriction(Restriction.ApprovalRequired);
    }
    public void setToRestricted(){
        setRestriction(Restriction.Restricted);
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
    @ManyToOne
    @JoinColumn(name = "reservationId",nullable = true)
    private Reservation booking;
}

