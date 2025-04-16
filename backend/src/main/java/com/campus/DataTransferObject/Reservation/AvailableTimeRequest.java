package com.campus.DataTransferObject.Reservation;

import com.campus.DataTransferObject.Resource.ResourceDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AvailableTimeRequest {
    public AvailableTimeRequest(){}
    public AvailableTimeRequest(ResourceDTO resourceDTO, String formattedDate){
        setResourceDTO(resourceDTO);
        setFormattedDate(formattedDate);
    }
    @JsonProperty
    private ResourceDTO resourceDTO;
    @JsonFormat(pattern="yyyy-MM-dd")
    private String formattedDate;
    public ResourceDTO getResourceDTO() {
        return resourceDTO;
    }
    public String getFormattedDate() {
        return formattedDate;
    }
    public LocalDate getLocalDate() {
        return LocalDate.parse(formattedDate);
    }
    public void setResourceDTO(ResourceDTO resourceDTO) {
        this.resourceDTO = resourceDTO;
    }
    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }
}
