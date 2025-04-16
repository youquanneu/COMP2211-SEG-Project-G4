package com.campus.DataTransferObject.Event;

import com.campus.Entity.Event.EmergencyCase;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class EmergencyCaseDTO {
    public EmergencyCaseDTO(){}
    public EmergencyCaseDTO(String reporterEmail,
                            String location,
                            String emergencyCase,
                            String description){
        setReporterEmail(reporterEmail);
        setLocation(location);
        setEmergencyCase(emergencyCase);
        setDescription(description);
    }
    public static EmergencyCaseDTO mapper(EmergencyCase emergencyCase){
        return new EmergencyCaseDTO(
                emergencyCase.getReporterEmail(),
                emergencyCase.getLocation().getResourceName(),
                emergencyCase.getEmergencyCase(),
                emergencyCase.getDescription());
    }
    public static List<EmergencyCaseDTO> listMapper(List<EmergencyCase> emergencyCaseList){
        List<EmergencyCaseDTO> emergencyCaseDTOS = new ArrayList<>();
        for (EmergencyCase emergencyCase : emergencyCaseList){
            emergencyCaseDTOS.add(mapper(emergencyCase));
        }
        return emergencyCaseDTOS;
    }
    @JsonProperty
    private String reporterEmail;
    @JsonProperty
    private String location;
    @JsonProperty
    private String emergencyCase;
    @JsonProperty
    private String description;
    public String getReporterEmail() {
        return reporterEmail;
    }
    public String getLocation() {
        return location;
    }
    public String getDescription() {
        return description;
    }
    public String getEmergencyCase() {
        return emergencyCase;
    }
    private void setReporterEmail(String reporterEmail) {
        this.reporterEmail = reporterEmail;
    }
    private void setLocation(String location) {
        this.location = location;
    }
    private void setDescription(String description) {
        this.description = description;
    }
    private void setEmergencyCase(String emergencyCase) {
        this.emergencyCase = emergencyCase;
    }
}
