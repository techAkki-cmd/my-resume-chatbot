package com.arijit.chatbotresume.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Integer skillId;

    @Column(name = "skill_name")
    private String skillName;

    @Column(name = "skill_type")
    private String skillType;

    // Constructors
    public Skills() {
    }

    public Skills(String skillName, String skillType) {
        this.skillName = skillName;
        this.skillType = skillType;
    }

    // Getters and setters
    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillType() {
        return skillType;
    }

    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }
}