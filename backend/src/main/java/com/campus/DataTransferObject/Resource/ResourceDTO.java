package com.campus.DataTransferObject.Resource;

import java.time.LocalTime;

public class ResourceDTO {
    public ResourceDTO(){}
    public ResourceDTO(String resourceName,
                       LocalTime openTime, LocalTime closeTime){
        setResourceName(resourceName);
        setOpenTime(openTime);
        setCloseTime(closeTime);
    }
    private String resourceName;
    private LocalTime openTime;
    private LocalTime closeTime;
    public String getResourceName() {
        return resourceName;
    }
    public LocalTime getOpenTime() {
        return openTime;
    }
    public LocalTime getCloseTime() {
        return closeTime;
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
