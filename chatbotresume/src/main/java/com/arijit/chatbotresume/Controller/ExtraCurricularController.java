package com.arijit.chatbotresume.Controller;

import com.arijit.chatbotresume.Model.ExtraCurricular;
import com.arijit.chatbotresume.Service.ExtraCurricularService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/extracurricular")
public class ExtraCurricularController {

    private final ExtraCurricularService extraCurricularService;

    public ExtraCurricularController(ExtraCurricularService extraCurricularService) {
        this.extraCurricularService = extraCurricularService;
    }

    // GET all
    @GetMapping
    public List<ExtraCurricular> getAllActivities() {
        return extraCurricularService.getAllActivities();
    }

    // GET by ID
    @GetMapping("/{id}")
    public ExtraCurricular getActivityById(@PathVariable Integer id) {
        return extraCurricularService.getActivityById(id);
    }

    // CREATE
    @PostMapping
    public ExtraCurricular createActivity(@RequestBody ExtraCurricular activity) {
        return extraCurricularService.saveActivity(activity);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ExtraCurricular updateActivity(@PathVariable Integer id, @RequestBody ExtraCurricular updatedActivity) {
        ExtraCurricular existing = extraCurricularService.getActivityById(id);
        existing.setPosition(updatedActivity.getPosition());
        existing.setOrganization(updatedActivity.getOrganization());
        existing.setResponsibilities(updatedActivity.getResponsibilities());
        return extraCurricularService.saveActivity(existing);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable Integer id) {
        extraCurricularService.deleteActivity(id);
    }
}