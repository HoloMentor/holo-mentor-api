package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.auth.AuthInstitutesDTO;
import com.holomentor.holomentor.dto.auth.AuthLoginDTO;
import com.holomentor.holomentor.dto.auth.AuthRegisterDTO;
import com.holomentor.holomentor.services.AuthService;
import com.holomentor.holomentor.utils.Response;
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
        authService.create(body);

        return Response.generate("user registration successful", HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthLoginDTO body){
        return authService.login(body);
    }

    @GetMapping("/institutes/{email}")
    public ResponseEntity<Object> institutes(@PathVariable @Valid String email){
        return authService.institutes(email);
    }
}
