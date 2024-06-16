package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/get")
    public String get() {
        return "user";
    }
}
