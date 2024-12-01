package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.staff.StaffCreateDTO;
import com.holomentor.holomentor.services.StaffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/{instituteId}/{teacherId}")
    public ResponseEntity<Object> getInstituteTeacherStaff(
            @Valid @PathVariable Long instituteId,
            @Valid @PathVariable Long teacherId,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "5") Integer size) {
        return staffService.getStaffByTeacherAndInstitute(teacherId, instituteId, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable @Valid Long id) {
        return (ResponseEntity<Object>) staffService.get(id);
    }

    @GetMapping("/teacher/{userId}/{instituteId}")
    public ResponseEntity<Object> get(@PathVariable @Valid Long userId, @Valid @PathVariable Long instituteId) {
        return (ResponseEntity<Object>) staffService.getTeacherData(userId,instituteId);
    }
    @GetMapping("/teacher/count/{userId}/{instituteId}")
    public ResponseEntity<Object> getTeacherStaffCount(@PathVariable @Valid Long userId, @Valid @PathVariable Long instituteId) {
        return (ResponseEntity<Object>) staffService.getTeacherStaffCount(userId,instituteId);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createStaff(@Valid @RequestBody StaffCreateDTO body) throws IOException {
        return staffService.create(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStaff(@PathVariable @Valid Long id) {
        return staffService.delete(id);
    }
}
