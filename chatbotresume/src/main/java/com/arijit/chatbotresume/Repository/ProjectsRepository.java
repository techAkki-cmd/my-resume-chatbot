package com.arijit.chatbotresume.Repository;

import com.arijit.chatbotresume.Model.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Integer> {

}