package com.campus.Entity.Event;

import com.campus.Classification.Status;
import com.campus.Entity.Resource.Venue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class EmergencyCase {
    public EmergencyCase(){}
    public EmergencyCase(Venue venue, String content, String reporter){
        setLocation(venue);
        setContent(content);
        setReporter(reporter);
        setReportedTime(LocalDateTime.now());
        setStatus(Status.Pending);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer emergencyCaseId;
    private Venue location;
    private String content;
    private String reporter;
    private LocalDateTime reportedTime;
    private Status status;
    public Integer getEmergencyCaseId() {
        return emergencyCaseId;
    }
    public Venue getLocation() {
        return location;
    }
    public String getContent() {
        return content;
    }
    public String getReporter() {
        return reporter;
    }
    public LocalDateTime getReportedTime() {
        return reportedTime;
    }
    public Status getStatus() {
        return status;
    }
    private void setContent(String content) {
        this.content = content;
    }
    private void setLocation(Venue location) {
        this.location = location;
    }
    private void setReporter(String reporter) {
        this.reporter = reporter;
    }
    private void setReportedTime(LocalDateTime reportedTime) {
        this.reportedTime = reportedTime;
    }
    private void setStatus(Status status) {
        this.status = status;
    }
}
