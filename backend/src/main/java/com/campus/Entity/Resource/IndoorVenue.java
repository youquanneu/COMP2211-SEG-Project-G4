package com.campus.Entity.Resource;

import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"building","roomNumber"}))
public class IndoorVenue extends Resource{
    public IndoorVenue(){}
    public IndoorVenue(String resourceName,
                       LocalTime openTime, LocalTime closeTime,
                       Restriction restriction,
                       String building, String roomNumber){
        super(resourceName, openTime, closeTime, restriction, ResourceCategory.IndoorVenue);
        setBuilding(building);
        setRoomNumber(roomNumber);
    }
    private String building;
    private String roomNumber;
    public void changeBuilding(String building){
        setBuilding(building);
    }
    public void changeRoomNumber(String roomNumber){
        setRoomNumber(roomNumber);
    }
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
