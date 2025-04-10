package com.arijit.chatbotresume.Service;

import com.arijit.chatbotresume.Model.Experience;
import com.arijit.chatbotresume.Repository.ExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    public ExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    public Experience getExperienceById(Integer id) {
        return experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience record not found"));
    }

    public Experience saveExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    public void deleteExperience(Integer id) {
        experienceRepository.deleteById(id);
    }
}