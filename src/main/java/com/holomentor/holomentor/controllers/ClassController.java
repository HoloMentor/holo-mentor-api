package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.classes.ClassCreateDTO;
import com.holomentor.holomentor.dto.classes.ClassUpdateDTO;
import com.holomentor.holomentor.dto.subject.SubjectUpdateDTO;
import com.holomentor.holomentor.services.ClassService;
import com.holomentor.holomentor.services.ClassTopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody ClassCreateDTO body) {
        return classService.create(body);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@Valid @PathVariable Long id) {
        return classService.delete(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> update(@Valid @PathVariable Long id, @Valid @RequestBody ClassUpdateDTO body) {
        return classService.update(id, body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return classService.get(id);
    }

    @GetMapping("/institute/{id}")
    public ResponseEntity<Object> institute(
            @PathVariable Long id,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "page", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "limit", defaultValue = "10") Integer pageSize) {
        return classService.findByInstituteId(id, search, pageNo, pageSize);
    }

    @GetMapping("/student/stats/{InstituteId}/{TeacherId}")
    public ResponseEntity<Object> getClassStudentStat(@PathVariable Long InstituteId ,@PathVariable Long TeacherId){
        return classService.getClassStudentStat(InstituteId,TeacherId);
    }


}