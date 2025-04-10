package com.arijit.chatbotresume.Repository;

import com.arijit.chatbotresume.Model.Honors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HonorsRepository extends JpaRepository<Honors, Integer> {

}