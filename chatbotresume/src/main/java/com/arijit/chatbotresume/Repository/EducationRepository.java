package com.arijit.chatbotresume.Repository;

import com.arijit.chatbotresume.Model.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {

}