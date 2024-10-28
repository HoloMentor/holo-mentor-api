package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.user.UserUpdateDTO;
import com.holomentor.holomentor.dto.user.UserUpdateInfoDTO;
import com.holomentor.holomentor.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class  UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable @Valid Long id) {
        return userService.get(id);
    }

    @PatchMapping("/update/user/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable @Valid Long id, @RequestBody @Valid UserUpdateDTO body) {
        return userService.updateUser(id, body);
    }

    @PatchMapping("/update/info/{id}")
    public ResponseEntity<Object> updateInfo(@PathVariable @Valid Long id, @RequestBody @Valid UserUpdateInfoDTO body) {
        return userService.updateInfo(id, body);
    }
}