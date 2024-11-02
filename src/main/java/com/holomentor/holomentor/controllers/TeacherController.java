package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.teacher.TeacherCreateDTO;
import com.holomentor.holomentor.dto.teacher.TeacherUpdateDTO;
import com.holomentor.holomentor.services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody TeacherCreateDTO body) throws IOException {
        return teacherService.create(body);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return teacherService.delete(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> update(@Valid @PathVariable Long id, @Valid @RequestBody TeacherUpdateDTO body) {
        return teacherService.update(id, body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@Valid @PathVariable Long id) {
        return teacherService.get(id);
    }

    @GetMapping("/stats/{id}")
    public ResponseEntity<Object> getTeacherStats(@Valid @PathVariable Long id) {
        return teacherService.getTeacherStats(id);
    }

    @GetMapping("/institute/classes/{id}")
    public ResponseEntity<Object> getInstituteTeacherClasses(
            @PathVariable Long id,
            @RequestParam(name="page", defaultValue = "1") Integer pageNo,
            @RequestParam(name="limit", defaultValue = "10") Integer pageSize) {
        return teacherService.getInstituteTeacherClasses(id, pageNo, pageSize);
    }

    @GetMapping("/institute/{id}")
    public ResponseEntity<Object> institute(
            @PathVariable Long id,
            @RequestParam(name="search", defaultValue = "") String search,
            @RequestParam(name="page", defaultValue = "1") Integer pageNo,
            @RequestParam(name="limit", defaultValue = "10") Integer pageSize) {
        return teacherService.getTeachersByInstituteId(id, search, pageNo, pageSize);
    }

}
