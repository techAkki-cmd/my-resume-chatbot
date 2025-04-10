package com.arijit.chatbotresume.Model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "certifications")
public class Certifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_id")
    private Integer certificationId;

    @Column(name = "certification_name")
    private String certificationName;

    private String organization;

    @Column(name = "completion_status")
    private String completionStatus;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    // Constructors
    public Certifications() {
    }

    public Certifications(String certificationName, String organization, String completionStatus, LocalDate completionDate) {
        this.certificationName = certificationName;
        this.organization = organization;
        this.completionStatus = completionStatus;
        this.completionDate = completionDate;
    }

    // Getters and setters
    public Integer getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(Integer certificationId) {
        this.certificationId = certificationId;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
}