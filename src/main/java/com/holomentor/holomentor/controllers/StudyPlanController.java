package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.studyPlan.StudyPlanCreateDTO;
import com.holomentor.holomentor.dto.studyPlan.StudyPlanUpdateDTO;
import com.holomentor.holomentor.dto.studyPlanTask.StudyPlanTaskUpdateDTO;
import com.holomentor.holomentor.models.InstituteClassStudent;
import com.holomentor.holomentor.repositories.InstituteClassStudentRepository;
import com.holomentor.holomentor.services.StudyPlanService;
import com.holomentor.holomentor.utils.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("study-plan")
public class StudyPlanController {

    @Autowired
    private StudyPlanService studyPlanService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody StudyPlanCreateDTO body) {
        return studyPlanService.create(body);
    }

    @GetMapping("/{classId}/{tier}")
    public ResponseEntity<Object> getTierPlans(@Valid @PathVariable Long classId, @Valid @PathVariable Integer tier) {
        return studyPlanService.getTierPlans(classId, tier);
    }

    @PostMapping("/tiers/create")
    public ResponseEntity<Object> createTiers(@RequestParam(name = "class_id") Integer classId, @RequestParam(name = "marks_out_of") Integer marksOutOf, @RequestParam(name = "file") MultipartFile file) throws IOException {
        return studyPlanService.createTiers(classId, marksOutOf, file);
    }

    @GetMapping("/csv/{classId}")
    public ResponseEntity<byte[]> getCSV(@Valid @PathVariable Long classId) {
        return studyPlanService.getCSV(classId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Valid Long id) {
        return studyPlanService.delete(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable @Valid Long id, @RequestBody StudyPlanUpdateDTO body) {
        return studyPlanService.update(id, body);
    }
}
