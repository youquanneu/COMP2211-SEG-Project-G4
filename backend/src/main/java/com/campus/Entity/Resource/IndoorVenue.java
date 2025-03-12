package com.campus.Entity.Resource;

import com.campus.EntityClassification.ResourceCategory;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class IndoorVenue extends Resource{
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
