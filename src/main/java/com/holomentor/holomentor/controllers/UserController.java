package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.user.UserCreateDTO;
import com.holomentor.holomentor.dto.user.UserLoginDTO;
import com.holomentor.holomentor.services.UserService;
import com.holomentor.holomentor.utils.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserCreateDTO user) {
        userService.create(user);

        return Response.generate("user registration successful", HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public String get() {
        return "user";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO loginrequest){
        return userService.login(loginrequest);
    }
}