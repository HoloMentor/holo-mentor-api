package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.institute.InstituteUpdateDTO;
import com.holomentor.holomentor.dto.user.UserSuspendDTO;
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

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(
            @RequestParam(name="search", defaultValue = "") String search,
            @RequestParam(name="page", defaultValue = "1") Integer pageNo,
            @RequestParam(name="limit", defaultValue = "10") Integer pageSize) {
        return userService.getAll(search, pageNo, pageSize);
    }

    @PatchMapping("/update/user/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable @Valid Long id, @RequestBody @Valid UserUpdateDTO body) {
        return userService.updateUser(id, body);
    }

    @PatchMapping("/update/info/{id}")
    public ResponseEntity<Object> updateInfo(@PathVariable @Valid Long id, @RequestBody @Valid UserUpdateInfoDTO body) {
        return userService.updateInfo(id, body);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Valid Long id){
        return userService.delete(id);
    }

    @PatchMapping("/suspend/{id}")
    public ResponseEntity<Object> suspend(@PathVariable @Valid Long id, @RequestBody UserSuspendDTO body){
        return userService.suspend(id, body);
    }
}