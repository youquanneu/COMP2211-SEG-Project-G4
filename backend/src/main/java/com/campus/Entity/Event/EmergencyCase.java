package com.campus.Entity.Event;

import com.campus.Classification.Status;
import com.campus.Entity.Resource.Venue;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
public class EmergencyCase {
    public EmergencyCase(){}
    public EmergencyCase(Venue venue, String emergencyCase, String description, String reporterEmail){
        setReporterEmail(reporterEmail);
        setLocation(venue);
        setDescription(description);
        setEmergencyCase(emergencyCase);
        setReportedTime(LocalDateTime.now());
        setStatus(Status.Pending);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer emergencyCaseId;
    private String reporterEmail;
    @ManyToOne
    private Venue location;
    @NotNull
    private String emergencyCase;
    private String description;
    @NotNull
    private LocalDateTime reportedTime;
    @NotNull
    private Status status;
    public void solvedEmergencyCase(){
        setStatus(Status.Solved);
    }
    public Integer getEmergencyCaseId() {
        return emergencyCaseId;
    }
    public String getReporterEmail() {
        return reporterEmail;
    }
    public Venue getLocation() {
        return location;
    }
    public String getEmergencyCase() {
        return emergencyCase;
    }
    public String getDescription() {
        return description;
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
    private void setLocation(Venue location) {
        this.location = location;
    }
    private void setEmergencyCase(String emergencyCase) {
        this.emergencyCase = emergencyCase;
    }
    private void setDescription(String description) {
        this.description = description;
    }
    private void setReportedTime(LocalDateTime reportedTime) {
        this.reportedTime = reportedTime;
    }
    private void setStatus(Status status) {
        this.status = status;
    }
}
