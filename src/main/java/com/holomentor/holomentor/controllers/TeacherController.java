package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.subject.SubjectUpdateDTO;
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

    private final TeacherServices teacherServices;

    @Autowired
    public TeacherController(TeacherServices teacherServices) {
        this.teacherServices = teacherServices;
    }

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
        return teacherServices.fetchTeachersByInstituteId(id, search, pageNo, pageSize);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) { {return teacherServices.delete(id);}}

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> update(@Valid @PathVariable Long id, @Valid @RequestBody TeacherUpdateDTO body) {
        return teacherServices.update(id, body);
    }

}
