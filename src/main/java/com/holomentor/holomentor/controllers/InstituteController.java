package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.institute.InstituteCreateDTO;
import com.holomentor.holomentor.dto.institute.InstituteUpdateDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable @Valid Long id) {
        return instituteService.get(id);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(
            @RequestParam(name="search", defaultValue = "") String search,
            @RequestParam(name="page", defaultValue = "1") Integer pageNo,
            @RequestParam(name="limit", defaultValue = "10") Integer pageSize) {
        return instituteService.getAll(search, pageNo, pageSize);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody InstituteCreateDTO body) throws IOException {
        return instituteService.create(body);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Valid Long id){
        return instituteService.delete(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Valid Long id, @RequestBody InstituteUpdateDTO body){
        return instituteService.update(id, body);
    }
}