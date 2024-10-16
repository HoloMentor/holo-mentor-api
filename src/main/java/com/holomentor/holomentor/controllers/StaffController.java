package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.staff.StaffCreateDTO;
import com.holomentor.holomentor.services.StaffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("staff/{instituteId}/{teacherId}")
    public ResponseEntity<Object> getInstituteTeacherStaff(
            @Valid @PathVariable Long instituteId,
            @Valid @PathVariable Long teacherId,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "page", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "limit", defaultValue = "5") Integer pageSize) {
        return (ResponseEntity<Object>) staffService.getInstituteTeacherStaff(instituteId, teacherId, search, pageNo, pageSize);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable @Valid Long id) {
        return (ResponseEntity<Object>) staffService.get(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createStaff(@Valid @RequestBody StaffCreateDTO body) {
        return staffService.create(body);
    }
}
