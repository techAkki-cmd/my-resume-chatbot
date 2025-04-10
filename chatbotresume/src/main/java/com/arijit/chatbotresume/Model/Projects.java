package com.arijit.chatbotresume.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "projects")
public class Projects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "project_name")
    private String projectName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "technologies_used")
    private String technologiesUsed;

    @Column(name = "project_link")
    private String projectLink;

    // Constructors
    public Projects() {
    }

    public Projects(String projectName, String description, String technologiesUsed, String projectLink) {
        this.projectName = projectName;
        this.description = description;
        this.technologiesUsed = technologiesUsed;
        this.projectLink = projectLink;
    }

    // Getters and setters
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnologiesUsed() {
        return technologiesUsed;
    }

    public void setTechnologiesUsed(String technologiesUsed) {
        this.technologiesUsed = technologiesUsed;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }
}