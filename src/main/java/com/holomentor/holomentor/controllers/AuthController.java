package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.auth.AuthLoginDTO;
import com.holomentor.holomentor.dto.auth.AuthRegisterDTO;
import com.holomentor.holomentor.services.AuthService;
import com.holomentor.holomentor.utils.Response;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Object> register(@RequestBody @Valid AuthRegisterDTO body) {
        return authService.create(body);
    }

    @PutMapping("/signup/super-admin")
    public ResponseEntity<Object> registerSuperAdmin(){
        return authService.createSuperAdmin();
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthLoginDTO body, HttpServletResponse response){
        return authService.login(body, response);
    }

    @GetMapping("/institutes/{email}")
    public ResponseEntity<Object> institutes(@PathVariable @Valid String email){
        return authService.institutes(email);
    }
}
