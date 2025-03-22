package com.campus.DataTransferObject.Resource;

import java.time.LocalTime;

public class IndoorVenueDTO extends VenueDTO{
    public IndoorVenueDTO(){}
    public IndoorVenueDTO(Integer resourceId, String resourceName,
                          LocalTime openTime, LocalTime closeTime,
                          String building, String roomNumber){
        super(resourceId,resourceName,openTime,closeTime);
        setBuilding(building);
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
