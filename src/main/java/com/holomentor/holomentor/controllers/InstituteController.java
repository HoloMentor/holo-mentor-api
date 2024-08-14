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

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(
            @RequestParam(name="search", defaultValue = "") String search,
            @RequestParam(name="page", defaultValue = "1") Integer pageNo,
            @RequestParam(name="limit", defaultValue = "10") Integer pageSize) throws IOException {
        return instituteService.getAll(search, pageNo, pageSize);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody InstituteCreateDTO body) throws IOException {
        return instituteService.create(body);
    }
}