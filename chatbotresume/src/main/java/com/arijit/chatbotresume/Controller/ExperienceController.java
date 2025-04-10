package com.arijit.chatbotresume.Controller;

import com.arijit.chatbotresume.Model.Experience;
import com.arijit.chatbotresume.Service.ExperienceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experience")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    // GET all
    @GetMapping
    public List<Experience> getAllExperiences() {
        return experienceService.getAllExperiences();
    }

    // GET by ID
    @GetMapping("/{id}")
    public Experience getExperienceById(@PathVariable Integer id) {
        return experienceService.getExperienceById(id);
    }

    // CREATE
    @PostMapping
    public Experience createExperience(@RequestBody Experience experience) {
        return experienceService.saveExperience(experience);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Experience updateExperience(@PathVariable Integer id, @RequestBody Experience updatedExp) {
        Experience existing = experienceService.getExperienceById(id);
        existing.setTitle(updatedExp.getTitle());
        existing.setCompany(updatedExp.getCompany());
        existing.setLocation(updatedExp.getLocation());
        existing.setStartDate(updatedExp.getStartDate());
        existing.setEndDate(updatedExp.getEndDate());
        existing.setResponsibilities(updatedExp.getResponsibilities());
        return experienceService.saveExperience(existing);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteExperience(@PathVariable Integer id) {
        experienceService.deleteExperience(id);
    }
}