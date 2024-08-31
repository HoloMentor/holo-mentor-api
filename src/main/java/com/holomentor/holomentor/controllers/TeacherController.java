package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.teacher.TeacherCreateDTO;
import com.holomentor.holomentor.dto.teacher.TeacherUpdateDTO;
import com.holomentor.holomentor.services.TeacherServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherServices teacherServices;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody TeacherCreateDTO body) {
        return teacherServices.createTeacher(body);
    }

    @GetMapping("/institute/{id}")
    public ResponseEntity<Object> institute(
            @PathVariable Long id,
            @RequestParam(name="search", defaultValue = "") String search,
            @RequestParam(name="page", defaultValue = "1") Integer pageNo,
            @RequestParam(name="limit", defaultValue = "10") Integer pageSize) {
        return teacherServices.getTeachersByInstituteId(id, search, pageNo, pageSize);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return teacherServices.delete(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> update(@Valid @PathVariable Long id, @Valid @RequestBody TeacherUpdateDTO body) {
        return teacherServices.update(id, body);
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Object> getTeacher(@Valid @PathVariable Long id) {
        return teacherServices.getTeacherById(id);
    }

    @GetMapping("/stats/{id}")
    public ResponseEntity<Object> getTeacherStats(@Valid @PathVariable Long id) {
        return teacherServices.getTeacherStats(id);
    }

    @GetMapping("/classes/{id}")
    public ResponseEntity<Object> getTeacherClasses(@PathVariable Long id,
                                                    @RequestParam(name="page", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name="limit", defaultValue = "10") Integer pageSize) {
        return teacherServices.getTeacherClasses(id, pageNo, pageSize);
    }

}
