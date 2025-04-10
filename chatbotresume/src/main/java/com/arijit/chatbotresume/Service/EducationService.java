package com.arijit.chatbotresume.Service;

import com.arijit.chatbotresume.Model.Education;
import com.arijit.chatbotresume.Repository.EducationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public List<Education> getAllEducation() {
        return educationRepository.findAll();
    }

    public Education getEducationById(Integer id) {
        return educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education record not found"));
    }

    public Education saveEducation(Education education) {
        return educationRepository.save(education);
    }

    public void deleteEducation(Integer id) {
        educationRepository.deleteById(id);
    }
}