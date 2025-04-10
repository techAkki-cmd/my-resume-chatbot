package com.arijit.chatbotresume.Repository;

import com.arijit.chatbotresume.Model.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Integer> {
    // Additional custom methods if needed
}