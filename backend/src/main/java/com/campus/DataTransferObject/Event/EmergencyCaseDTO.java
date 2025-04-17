package com.campus.DataTransferObject.Event;

import com.campus.Classification.Status;
import com.campus.DataTransferObject.Resource.VenueDTO;
import com.campus.Entity.Event.EmergencyCase;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmergencyCaseDTO {
    public EmergencyCaseDTO(){}
    public EmergencyCaseDTO(String reporterEmail,
                            VenueDTO location,
                            String emergencyCase,
                            String description,
                            LocalDateTime reportedTime,
                            Status status){
        setReporterEmail(reporterEmail);
        setLocation(location);
        setEmergencyCase(emergencyCase);
        setDescription(description);
        setReportedTime(reportedTime);
        setStatus(status);
    }
    public static EmergencyCaseDTO mapper(EmergencyCase emergencyCase){
        return new EmergencyCaseDTO(
                emergencyCase.getReporterEmail(),
                VenueDTO.mapper(emergencyCase.getLocation()),
                emergencyCase.getEmergencyCase(),
                emergencyCase.getDescription(),
                emergencyCase.getReportedTime(),
                emergencyCase.getStatus());
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
    private VenueDTO location;
    @JsonProperty
    private String emergencyCase;
    @JsonProperty
    private String description;
    @JsonProperty
    private LocalDateTime reportedTime;
    @JsonProperty("status")
    private Status status;
    public String getReporterEmail() {
        return reporterEmail;
    }
    public VenueDTO getLocation() {
        return location;
    }
    public String getDescription() {
        return description;
    }
    public String getEmergencyCase() {
        return emergencyCase;
    }
    public LocalDateTime getReportedTime() {
        return reportedTime;
    }
    public Status getStatus() {
        return status;
    }
    private void setReporterEmail(String reporterEmail) {
        this.reporterEmail = reporterEmail;
    }
    private void setLocation(VenueDTO location) {
        this.location = location;
    }
    private void setDescription(String description) {
        this.description = description;
    }
    private void setEmergencyCase(String emergencyCase) {
        this.emergencyCase = emergencyCase;
    }
    private void setReportedTime(LocalDateTime reportedTime) {
        this.reportedTime = reportedTime;
    }
    private void setStatus(Status status) {
        this.status = status;
    }
}
