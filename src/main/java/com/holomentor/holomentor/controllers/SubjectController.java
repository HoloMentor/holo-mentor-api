package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.subject.SubjectCreateDTO;
import com.holomentor.holomentor.dto.subject.SubjectUpdateDTO;
import com.holomentor.holomentor.services.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody SubjectCreateDTO body) {
        return subjectService.create(body);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> update(@Valid @PathVariable Long id, @Valid @RequestBody SubjectUpdateDTO body) {
        return subjectService.update(id, body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable @Valid Long id) {
        return subjectService.get(id);
    }

    @GetMapping("/institute/{instituteId}")
    public ResponseEntity<Object> getInstituteSubjects(
            @Valid @PathVariable Long instituteId,
            @RequestParam(name="search", defaultValue = "") String search,
            @RequestParam(name="page", defaultValue = "1") Integer pageNo,
            @RequestParam(name="limit", defaultValue = "10") Integer pageSize) {
        return subjectService.getInstituteSubjects(instituteId, search, pageNo, pageSize);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Valid Long id) {
        return subjectService.delete(id);
    }
}
