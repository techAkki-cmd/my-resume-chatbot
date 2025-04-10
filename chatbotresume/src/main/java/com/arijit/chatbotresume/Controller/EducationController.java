package com.arijit.chatbotresume.Controller;


import com.arijit.chatbotresume.Model.Education;
import com.arijit.chatbotresume.Service.EducationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education")
public class EducationController {

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    // GET all
    @GetMapping
    public List<Education> getAllEducation() {
        return educationService.getAllEducation();
    }

    // GET by ID
    @GetMapping("/{id}")
    public Education getEducationById(@PathVariable Integer id) {
        return educationService.getEducationById(id);
    }

    // CREATE
    @PostMapping
    public Education createEducation(@RequestBody Education education) {
        return educationService.saveEducation(education);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Education updateEducation(@PathVariable Integer id, @RequestBody Education updatedEdu) {
        Education existing = educationService.getEducationById(id);
        existing.setDegree(updatedEdu.getDegree());
        existing.setInstitute(updatedEdu.getInstitute());
        existing.setStartYear(updatedEdu.getStartYear());
        existing.setExpectedGraduationYear(updatedEdu.getExpectedGraduationYear());
        existing.setRelevantCoursework(updatedEdu.getRelevantCoursework());
        return educationService.saveEducation(existing);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteEducation(@PathVariable Integer id) {
        educationService.deleteEducation(id);
    }
}