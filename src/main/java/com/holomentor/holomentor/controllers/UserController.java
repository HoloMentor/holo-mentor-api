package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.user.UserUpdateDTO;
import com.holomentor.holomentor.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable @Valid Long id) {
        return userService.get(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable @Valid Long id, @RequestBody @Valid UserUpdateDTO body) {
        return userService.update(id, body);
    }
}