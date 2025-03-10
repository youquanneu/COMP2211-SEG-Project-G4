package com.campus.Entity;

import com.campus.EntityClassification.ResourceCategory;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Resource {
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
    @JoinColumn(name = "reservationId",nullable = false)
    private Reservation booking;

}
@Entity
class OutdoorVenue extends Resource{
    public OutdoorVenue(){}
    public OutdoorVenue(String resourceName,
                        LocalDateTime openTime, LocalDateTime closeTime){
        super(resourceName, openTime, closeTime, ResourceCategory.OutdoorVenue);
    }
}
@Entity
class IndoorVenue extends Resource{
    public IndoorVenue(){}
    public IndoorVenue(String roomNumber, String resourceName,
                       LocalDateTime openTime, LocalDateTime closeTime){
        super(resourceName, openTime, closeTime, ResourceCategory.IndoorVenue);
        setRoomNumber(roomNumber);
    }
    private String building;
    private String roomNumber;
    public String getBuilding() {
        return building;
    }
    public String getRoomNumber() {
        return roomNumber;
    }
    private void setBuilding(String building) {
        this.building = building;
    }
    private void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
@Entity
class Equipment extends Resource{
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