package com.campus.DataTransferObject.Event;

import com.campus.DataTransferObject.Resource.VenueDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmergencyCaseDTO {
    public EmergencyCaseDTO(){}
    public EmergencyCaseDTO(String reporterEmail,
                            VenueDTO venueDTO,
                            String emergencyCase,
                            String description){
        setReporterEmail(reporterEmail);
        setVenueDTO(venueDTO);
        setEmergencyCase(emergencyCase);
        setDescription(description);
    }
    @JsonProperty
    private String reporterEmail;
    @JsonProperty
    private VenueDTO venueDTO;
    @JsonProperty
    private String emergencyCase;
    @JsonProperty
    private String description;
    public String getReporterEmail() {
        return reporterEmail;
    }
    public VenueDTO getVenueDTO() {
        return venueDTO;
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
    private void setVenueDTO(VenueDTO venueDTO) {
        this.venueDTO = venueDTO;
    }
    private void setDescription(String description) {
        this.description = description;
    }
    private void setEmergencyCase(String emergencyCase) {
        this.emergencyCase = emergencyCase;
    }
}
