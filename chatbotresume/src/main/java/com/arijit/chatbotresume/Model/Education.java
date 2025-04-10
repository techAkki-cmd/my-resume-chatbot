package com.arijit.chatbotresume.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "education")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id")
    private Integer educationId;

    private String degree;
    private String institute;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "expected_graduation_year")
    private Integer expectedGraduationYear;

    @Column(name = "relevant_coursework")
    private String relevantCoursework;

    // Constructors, getters, and setters
    public Education() {
    }

    public Education(String degree, String institute, Integer startYear,
                     Integer expectedGraduationYear, String relevantCoursework) {
        this.degree = degree;
        this.institute = institute;
        this.startYear = startYear;
        this.expectedGraduationYear = expectedGraduationYear;
        this.relevantCoursework = relevantCoursework;
    }

    public Integer getEducationId() {
        return educationId;
    }

    public void setEducationId(Integer educationId) {
        this.educationId = educationId;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getExpectedGraduationYear() {
        return expectedGraduationYear;
    }

    public void setExpectedGraduationYear(Integer expectedGraduationYear) {
        this.expectedGraduationYear = expectedGraduationYear;
    }

    public String getRelevantCoursework() {
        return relevantCoursework;
    }

    public void setRelevantCoursework(String relevantCoursework) {
        this.relevantCoursework = relevantCoursework;
    }
}