package com.campus.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Resource {
    public Resource(){}
    public Resource(String resourceName, LocalDateTime openTime, LocalDateTime closeTime){
        setResourceName(resourceName);
        setOpenTime(openTime);
        setCloseTime(closeTime);
    }
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

    private void setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
    }
    private void setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
    }
    private void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resourceId;
    private String resourceName;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    @ManyToOne
    @JoinColumn(name = "reservationId",nullable = false)
    private Reservation booking;
}
@Entity
class OutdoorVenue extends Resource{
}
@Entity
class IndoorVenue extends Resource{
    public IndoorVenue(String roomNumber, String resourceName, LocalDateTime openTime, LocalDateTime closeTime){
        super(resourceName, openTime, closeTime);
        setRoomNumber(roomNumber);
    }
    private String roomNumber;
    public String getRoomNumber() {
        return roomNumber;
    }
    private void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
@Entity
class Equipment extends Resource{
    public Equipment(String serialNumber, String resourceName, LocalDateTime openTime, LocalDateTime closeTime){
        super(resourceName, openTime, closeTime);
        setSerialNumber(serialNumber);
    }
    private String serialNumber;
    public String getSerialNumber() {
        return serialNumber;
    }
    private void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    private String Location;
}