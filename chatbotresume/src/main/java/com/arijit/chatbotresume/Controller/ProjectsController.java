package com.arijit.chatbotresume.Controller;

import com.arijit.chatbotresume.Model.Projects;
import com.arijit.chatbotresume.Service.ProjectsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectsController {

    private final ProjectsService projectsService;

    public ProjectsController(ProjectsService projectsService) {
        this.projectsService = projectsService;
    }

    // GET all
    @GetMapping
    public List<Projects> getAllProjects() {
        return projectsService.getAllProjects();
    }

    // GET by ID
    @GetMapping("/{id}")
    public Projects getProjectById(@PathVariable Integer id) {
        return projectsService.getProjectById(id);
    }

    // CREATE
    @PostMapping
    public Projects createProject(@RequestBody Projects project) {
        return projectsService.saveProject(project);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Projects updateProject(@PathVariable Integer id, @RequestBody Projects updatedProject) {
        Projects existing = projectsService.getProjectById(id);
        existing.setProjectName(updatedProject.getProjectName());
        existing.setDescription(updatedProject.getDescription());
        existing.setTechnologiesUsed(updatedProject.getTechnologiesUsed());
        existing.setProjectLink(updatedProject.getProjectLink());
        return projectsService.saveProject(existing);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Integer id) {
        projectsService.deleteProject(id);
    }
}