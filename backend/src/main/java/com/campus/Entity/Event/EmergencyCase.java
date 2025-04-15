package com.campus.Entity.Event;

import com.campus.Classification.Status;
import com.campus.Entity.Resource.Venue;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

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
    @ManyToOne
    private Venue location;
    @NotNull
    private String content;
    @NotNull
    private String reporter;
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
