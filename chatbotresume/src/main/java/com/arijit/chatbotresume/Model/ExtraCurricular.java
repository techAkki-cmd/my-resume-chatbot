package com.arijit.chatbotresume.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "extracurricular") // must match the exact table name
public class ExtraCurricular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Integer activityId;

    private String position;
    private String organization;

    @Column(columnDefinition = "TEXT")
    private String responsibilities;

    // Constructors
    public ExtraCurricular() {
    }

    public ExtraCurricular(String position, String organization, String responsibilities) {
        this.position = position;
        this.organization = organization;
        this.responsibilities = responsibilities;
    }

    // Getters and setters
    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }
}