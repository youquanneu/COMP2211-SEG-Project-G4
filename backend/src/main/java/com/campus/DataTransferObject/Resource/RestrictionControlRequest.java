package com.campus.DataTransferObject.Resource;

import com.campus.Classification.Restriction;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RestrictionControlRequest {
    public RestrictionControlRequest(){}
    public RestrictionControlRequest(ResourceDTO resourceDTO, Restriction restriction){
        setResourceDTO(resourceDTO);
        setRestriction(restriction);
    }
    @JsonProperty
    private ResourceDTO resourceDTO;
    @JsonProperty("restriction")
    private Restriction restriction;
    public ResourceDTO getResourceDTO() {
        return resourceDTO;
    }
    public Restriction getRestriction() {
        return restriction;
    }
    private void setResourceDTO(ResourceDTO resourceDTO) {
        this.resourceDTO = resourceDTO;
    }
    private void setRestriction(Restriction restriction) {
        this.restriction = restriction;
    }
}
