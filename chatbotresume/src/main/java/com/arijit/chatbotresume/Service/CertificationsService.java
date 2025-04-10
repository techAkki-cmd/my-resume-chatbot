package com.arijit.chatbotresume.Service;

import com.arijit.chatbotresume.Model.Certifications;
import com.arijit.chatbotresume.Repository.CertificationsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationsService {

    private final CertificationsRepository certificationsRepository;

    public CertificationsService(CertificationsRepository certificationsRepository) {
        this.certificationsRepository = certificationsRepository;
    }

    public List<Certifications> getAllCertifications() {
        return certificationsRepository.findAll();
    }

    public Certifications getCertificationById(Integer id) {
        return certificationsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certification not found"));
    }

    public Certifications saveCertification(Certifications certification) {
        return certificationsRepository.save(certification);
    }

    public void deleteCertification(Integer id) {
        certificationsRepository.deleteById(id);
    }
}