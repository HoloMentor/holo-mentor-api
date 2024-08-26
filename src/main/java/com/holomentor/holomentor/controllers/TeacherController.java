package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.teacher.TeacherCreateDTO;
import com.holomentor.holomentor.services.TeacherServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

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
}
