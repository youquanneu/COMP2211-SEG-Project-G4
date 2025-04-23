package com.campus.DataTransferObject.Resource;

import com.campus.Entity.Resource.Resource;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ResourceDTO {
    public ResourceDTO(){}
    public ResourceDTO(Integer resourceId, String resourceName,
                       LocalTime openTime, LocalTime closeTime){
        setResourceId(resourceId);
        setResourceName(resourceName);
        setOpenTime(openTime);
        setCloseTime(closeTime);
    }
    public static ResourceDTO mapper(Resource resource){
        return new ResourceDTO(
                resource.getResourceId(),
                resource.getResourceName(),
                resource.getOpenTime(),
                resource.getCloseTime());
    }
    public static List<ResourceDTO> listMapper(List<Resource>resources){
        List<ResourceDTO> resourceDTOS = new ArrayList<>();
        for (Resource resource: resources){
            resourceDTOS.add(mapper(resource));
        }
        return resourceDTOS;
    }
    @JsonProperty
    private Integer resourceId;
    @JsonProperty
    private String resourceName;
    @JsonProperty
    private LocalTime openTime;
    @JsonProperty
    private LocalTime closeTime;
    public Integer getResourceId() {
        return resourceId;
    }
    public String getResourceName() {
        return resourceName;
    }
    public LocalTime getOpenTime() {
        return openTime;
    }
    public LocalTime getCloseTime() {
        return closeTime;
    }
    private void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
    private void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    private void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }
    private void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }
}
