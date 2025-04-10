package com.arijit.chatbotresume.Controller;

import com.arijit.chatbotresume.Model.Certifications;
import com.arijit.chatbotresume.Service.CertificationsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certifications")
public class CertificationsController {

    private final CertificationsService certificationsService;

    public CertificationsController(CertificationsService certificationsService) {
        this.certificationsService = certificationsService;
    }

    // GET all
    @GetMapping
    public List<Certifications> getAllCertifications() {
        return certificationsService.getAllCertifications();
    }

    // GET by ID
    @GetMapping("/{id}")
    public Certifications getCertificationById(@PathVariable Integer id) {
        return certificationsService.getCertificationById(id);
    }

    // CREATE
    @PostMapping
    public Certifications createCertification(@RequestBody Certifications certification) {
        return certificationsService.saveCertification(certification);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Certifications updateCertification(@PathVariable Integer id, @RequestBody Certifications updatedCert) {
        Certifications existing = certificationsService.getCertificationById(id);
        existing.setCertificationName(updatedCert.getCertificationName());
        existing.setOrganization(updatedCert.getOrganization());
        existing.setCompletionStatus(updatedCert.getCompletionStatus());
        existing.setCompletionDate(updatedCert.getCompletionDate());
        return certificationsService.saveCertification(existing);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteCertification(@PathVariable Integer id) {
        certificationsService.deleteCertification(id);
    }
}