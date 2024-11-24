package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.services.StudentService;
import com.holomentor.holomentor.dto.student.StudentCreateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("students")
public class StudentController {


    @Autowired
    private StudentService studentServices;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody StudentCreateDTO body) throws IOException {
        return studentServices.create(body);
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<Object> getInstituteStudentClasses(
            @PathVariable Long classId,
            @RequestParam(name="page", defaultValue = "1") Integer pageNo,
            @RequestParam(name="limit", defaultValue = "10") Integer pageSize) {
        return studentServices.findStudentsByClassId(classId, pageNo, pageSize);
    }


}
