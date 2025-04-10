package com.arijit.chatbotresume.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "honors")
public class Honors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "honor_id")
    private Integer honorId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Constructors
    public Honors() {
    }

    public Honors(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Getters and setters
    public Integer getHonorId() {
        return honorId;
    }

    public void setHonorId(Integer honorId) {
        this.honorId = honorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}