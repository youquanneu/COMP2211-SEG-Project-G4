package com.campus.DataTransferObject.Resource;

import java.time.LocalTime;

public class ResourceDTO {
    public ResourceDTO(){}
    public ResourceDTO(Integer resourceId, String resourceName,
                       LocalTime openTime, LocalTime closeTime){
        setResourceId(resourceId);
        setResourceName(resourceName);
        setOpenTime(openTime);
        setCloseTime(closeTime);
    }
    private Integer resourceId;
    private String resourceName;
    private LocalTime openTime;
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
