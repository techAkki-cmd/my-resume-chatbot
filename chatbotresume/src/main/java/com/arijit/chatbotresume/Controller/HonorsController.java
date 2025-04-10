package com.arijit.chatbotresume.Controller;

import com.arijit.chatbotresume.Model.Honors;
import com.arijit.chatbotresume.Service.HonorsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/honors")
public class HonorsController {

    private final HonorsService honorsService;

    public HonorsController(HonorsService honorsService) {
        this.honorsService = honorsService;
    }

    // GET all
    @GetMapping
    public List<Honors> getAllHonors() {
        return honorsService.getAllHonors();
    }

    // GET by ID
    @GetMapping("/{id}")
    public Honors getHonorById(@PathVariable Integer id) {
        return honorsService.getHonorById(id);
    }

    // CREATE
    @PostMapping
    public Honors createHonor(@RequestBody Honors honor) {
        return honorsService.saveHonor(honor);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Honors updateHonor(@PathVariable Integer id, @RequestBody Honors updatedHonor) {
        Honors existing = honorsService.getHonorById(id);
        existing.setTitle(updatedHonor.getTitle());
        existing.setDescription(updatedHonor.getDescription());
        return honorsService.saveHonor(existing);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteHonor(@PathVariable Integer id) {
        honorsService.deleteHonor(id);
    }
}