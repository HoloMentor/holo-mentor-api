package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("staff")
public class StaffController {

    @Autowired
    private UserService staffService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable @Valid Long id) {
        return staffService.get(id);
    }


}
