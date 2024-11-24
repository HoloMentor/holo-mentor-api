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



}
