package com.campus.DataTransferObject.Resource;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DashboardDTO {
    public DashboardDTO(){}
    public DashboardDTO(Integer bookedVenue,
                        Integer availableVenue,
                        Integer bookedEquipment,
                        Integer availableEquipment){
        setBookedVenue(bookedVenue);
        setAvailableVenue(availableVenue);
        setBookedEquipment(bookedEquipment);
        setAvailableEquipment(availableEquipment);
    }
    @JsonProperty
    private Integer bookedVenue;
    @JsonProperty
    private Integer availableVenue;
    @JsonProperty
    private Integer bookedEquipment;
    @JsonProperty
    private Integer availableEquipment;
    public Integer getBookedEquipment() {
        return bookedEquipment;
    }
    public Integer getAvailableEquipment() {
        return availableEquipment;
    }
    public Integer getBookedVenue() {
        return bookedVenue;
    }
    public Integer getAvailableVenue() {
        return availableVenue;
    }
    private void setBookedVenue(Integer bookedVenue) {
        this.bookedVenue = bookedVenue;
    }
    private void setAvailableVenue(Integer availableVenue) {
        this.availableVenue = availableVenue;
    }
    private void setBookedEquipment(Integer bookedEquipment) {
        this.bookedEquipment = bookedEquipment;
    }
    private void setAvailableEquipment(Integer availableEquipment) {
        this.availableEquipment = availableEquipment;
    }
}
