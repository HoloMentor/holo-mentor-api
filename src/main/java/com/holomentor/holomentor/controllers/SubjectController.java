package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.subject.SubjectCreateDTO;
import com.holomentor.holomentor.dto.subject.SubjectUpdateDTO;
import com.holomentor.holomentor.services.SubjectServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("subjects")
public class SubjectController {

    @Autowired
    private SubjectServices subjectServices;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody SubjectCreateDTO body) {
        return subjectServices.create(body);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> update(@Valid @PathVariable Long id, @Valid @RequestBody SubjectUpdateDTO body) {
        return subjectServices.update(id, body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable @Valid Long id) {
        return subjectServices.get(id);
    }

    @GetMapping("/institute/{instituteId}")
    public ResponseEntity<Object> getInstituteSubjects(
            @Valid @PathVariable Long instituteId,
            @RequestParam(name="search", defaultValue = "") String search,
            @RequestParam(name="page", defaultValue = "1") Integer pageNo,
            @RequestParam(name="limit", defaultValue = "10") Integer pageSize) {
        return subjectServices.getInstituteSubjects(instituteId, search, pageNo, pageSize);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Valid Long id) {
        return subjectServices.delete(id);
    }
}
