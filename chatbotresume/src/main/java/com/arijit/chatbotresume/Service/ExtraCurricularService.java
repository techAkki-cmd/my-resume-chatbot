package com.arijit.chatbotresume.Service;

import com.arijit.chatbotresume.Model.ExtraCurricular;
import com.arijit.chatbotresume.Repository.ExtraCurricularRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtraCurricularService {

    private final ExtraCurricularRepository extraCurricularRepository;

    public ExtraCurricularService(ExtraCurricularRepository extraCurricularRepository) {
        this.extraCurricularRepository = extraCurricularRepository;
    }

    public List<ExtraCurricular> getAllActivities() {
        return extraCurricularRepository.findAll();
    }

    public ExtraCurricular getActivityById(Integer id) {
        return extraCurricularRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
    }

    public ExtraCurricular saveActivity(ExtraCurricular activity) {
        return extraCurricularRepository.save(activity);
    }

    public void deleteActivity(Integer id) {
        extraCurricularRepository.deleteById(id);
    }
}