package com.arijit.chatbotresume.Service;

import com.arijit.chatbotresume.Model.Projects;
import com.arijit.chatbotresume.Repository.ProjectsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectsService {

    private final ProjectsRepository projectsRepository;

    public ProjectsService(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    public List<Projects> getAllProjects() {
        return projectsRepository.findAll();
    }

    public Projects getProjectById(Integer id) {
        return projectsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public Projects saveProject(Projects project) {
        return projectsRepository.save(project);
    }

    public void deleteProject(Integer id) {
        projectsRepository.deleteById(id);
    }
}