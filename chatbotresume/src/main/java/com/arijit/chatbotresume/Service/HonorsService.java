package com.arijit.chatbotresume.Service;

import com.arijit.chatbotresume.Model.Honors;
import com.arijit.chatbotresume.Repository.HonorsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HonorsService {

    private final HonorsRepository honorsRepository;

    public HonorsService(HonorsRepository honorsRepository) {
        this.honorsRepository = honorsRepository;
    }

    public List<Honors> getAllHonors() {
        return honorsRepository.findAll();
    }

    public Honors getHonorById(Integer id) {
        return honorsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Honor not found"));
    }

    public Honors saveHonor(Honors honor) {
        return honorsRepository.save(honor);
    }

    public void deleteHonor(Integer id) {
        honorsRepository.deleteById(id);
    }
}