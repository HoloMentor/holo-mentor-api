package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.institute.InstituteUpdateDTO;
import com.holomentor.holomentor.dto.studyPlan.StudyPlanCreateTaskDTO;
import com.holomentor.holomentor.dto.studyPlanTask.StudyPlanTaskCreateDTO;
import com.holomentor.holomentor.dto.studyPlanTask.StudyPlanTaskUpdateDTO;
import com.holomentor.holomentor.services.StudyPlanTaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("study-plan/task")
public class StudyPlanTaskController {

    @Autowired
    private StudyPlanTaskService studyPlanTaskService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody StudyPlanTaskCreateDTO body) {
        return studyPlanTaskService.create(body);
    }

    @GetMapping("/{classId}/{studyPlanId}")
    public ResponseEntity<Object> getPlanTasks(@Valid @PathVariable Long classId, @Valid @PathVariable Long studyPlanId) {
        return studyPlanTaskService.getPlanTasks(classId, studyPlanId);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Valid Long id, @RequestBody StudyPlanTaskUpdateDTO body){
        return studyPlanTaskService.update(id, body);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Valid Long id) {
        return studyPlanTaskService.delete(id);
    }
}
