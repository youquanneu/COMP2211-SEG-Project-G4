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
    private String roomNumber;
}
@Entity
class Equipment extends Resource{
    private String serialNumber;
}