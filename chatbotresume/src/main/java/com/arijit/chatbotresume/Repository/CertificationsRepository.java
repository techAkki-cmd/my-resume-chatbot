package com.arijit.chatbotresume.Repository;

import com.arijit.chatbotresume.Model.Certifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationsRepository extends JpaRepository<Certifications, Integer> {
    // Optionally define custom query methods here if needed
}