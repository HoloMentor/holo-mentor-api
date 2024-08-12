package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.institute.InstituteCreateDTO;
import com.holomentor.holomentor.services.InstituteService;
import com.holomentor.holomentor.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("institutes")
public class InstituteController {

    @Autowired
    private InstituteService instituteService;

    @GetMapping("/get")
    public String get() {
        return "institutes";
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody InstituteCreateDTO body) throws IOException {
        return instituteService.create(body);
    }
}