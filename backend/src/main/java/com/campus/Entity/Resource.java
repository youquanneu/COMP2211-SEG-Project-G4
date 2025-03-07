package com.campus.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Resource {
    public Resource(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String resourceName;
    private LocalDateTime reservationStarting;
    private LocalDateTime reservationEnding;
}
@Entity
class Venue extends Resource{
}
@Entity
class IndoorVenue extends Venue{
    private String roomNumber;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
}
@Entity
class Equipment extends  Resource{
    private String serialNumber;
}
enum ResourceType{

}