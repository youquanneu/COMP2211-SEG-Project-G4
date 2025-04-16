package com.campus.DataTransferObject.Reservation;

import com.campus.DataTransferObject.Resource.ResourceDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class AvailableTimeRequest {
    public AvailableTimeRequest(){}
    public AvailableTimeRequest(ResourceDTO resourceDTO, LocalDate localDate){
        setResourceDTO(resourceDTO);
        setLocalDate(localDate);
    }
    @JsonProperty
    private ResourceDTO resourceDTO;
    @JsonProperty
    private LocalDate localDate;
    public ResourceDTO getResourceDTO() {
        return resourceDTO;
    }
    public LocalDate getLocalDate() {
        return localDate;
    }
    public void setResourceDTO(ResourceDTO resourceDTO) {
        this.resourceDTO = resourceDTO;
    }
    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
