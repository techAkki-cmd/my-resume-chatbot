package com.arijit.chatbotresume.Repository;

import com.arijit.chatbotresume.Model.ExtraCurricular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraCurricularRepository extends JpaRepository<ExtraCurricular, Integer> {
    // Additional custom query methods can go here if needed
}