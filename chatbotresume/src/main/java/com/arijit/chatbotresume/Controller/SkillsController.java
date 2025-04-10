package com.arijit.chatbotresume.Controller;

import com.arijit.chatbotresume.Model.Skills;
import com.arijit.chatbotresume.Service.SkillsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillsController {

    private final SkillsService skillsService;

    public SkillsController(SkillsService skillsService) {
        this.skillsService = skillsService;
    }

    // GET all
    @GetMapping
    public List<Skills> getAllSkills() {
        return skillsService.getAllSkills();
    }

    // GET by ID
    @GetMapping("/{id}")
    public Skills getSkillById(@PathVariable Integer id) {
        return skillsService.getSkillById(id);
    }

    // CREATE
    @PostMapping
    public Skills createSkill(@RequestBody Skills skill) {
        return skillsService.saveSkill(skill);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Skills updateSkill(@PathVariable Integer id, @RequestBody Skills updatedSkill) {
        Skills existing = skillsService.getSkillById(id);
        existing.setSkillName(updatedSkill.getSkillName());
        existing.setSkillType(updatedSkill.getSkillType());
        return skillsService.saveSkill(existing);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteSkill(@PathVariable Integer id) {
        skillsService.deleteSkill(id);
    }
}