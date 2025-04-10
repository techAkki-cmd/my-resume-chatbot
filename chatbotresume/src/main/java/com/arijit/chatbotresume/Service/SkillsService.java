package com.arijit.chatbotresume.Service;

import com.arijit.chatbotresume.Model.Skills;
import com.arijit.chatbotresume.Repository.SkillsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillsService {

    private final SkillsRepository skillsRepository;

    public SkillsService(SkillsRepository skillsRepository) {
        this.skillsRepository = skillsRepository;
    }

    public List<Skills> getAllSkills() {
        return skillsRepository.findAll();
    }

    public Skills getSkillById(Integer id) {
        return skillsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
    }

    public Skills saveSkill(Skills skill) {
        return skillsRepository.save(skill);
    }

    public void deleteSkill(Integer id) {
        skillsRepository.deleteById(id);
    }
}