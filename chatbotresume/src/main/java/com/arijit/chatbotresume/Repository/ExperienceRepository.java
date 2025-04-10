package com.arijit.chatbotresume.Repository;

import com.arijit.chatbotresume.Model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    // Additional query methods if needed
}