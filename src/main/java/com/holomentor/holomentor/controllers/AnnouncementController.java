package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.announcement.AnnouncementCreateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.holomentor.holomentor.services.AnnouncementService;

import java.io.IOException;

@RestController
@RequestMapping("announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody AnnouncementCreateDTO body) throws IOException {
        return announcementService.create(body);
    }

    @GetMapping("/all/{institute_id}")
    public ResponseEntity<Object> getAll(@PathVariable Long institute_id) {
        return announcementService.getAllbyInstituteId(institute_id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return announcementService.deleteById(id);
    }
}
