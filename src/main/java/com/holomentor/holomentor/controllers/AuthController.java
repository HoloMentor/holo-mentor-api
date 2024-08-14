package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.auth.AuthInvitationAcceptDTO;
import com.holomentor.holomentor.dto.auth.AuthLoginDTO;
import com.holomentor.holomentor.dto.auth.AuthRegisterDTO;
import com.holomentor.holomentor.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.NotActiveException;

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
    public ResponseEntity<Object> login(@RequestBody @Valid AuthLoginDTO body, HttpServletResponse response) throws UsernameNotFoundException, NotActiveException {
        return authService.login(body, response);
    }

    @PostMapping("/signin/invitation")
    public ResponseEntity<Object> invitationLogin(@RequestBody @Valid AuthInvitationAcceptDTO body, HttpServletResponse response) throws UsernameNotFoundException, NotActiveException {
        return authService.invitationLogin(body, response);
    }

    @GetMapping("/invitation/{token}")
    public ResponseEntity<Object> invitationDetails(@PathVariable @Valid String token) {
        return authService.invitationDetails(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refresh(HttpServletRequest body, HttpServletResponse response) throws UsernameNotFoundException, NotActiveException {
        return authService.refresh(body, response);
    }

    @GetMapping("/institutes/{email}")
    public ResponseEntity<Object> institutes(@PathVariable @Valid String email){
        return authService.institutes(email);
    }

    @GetMapping("/me/{id}")
    public ResponseEntity<Object> authorize(@PathVariable @Valid String id){
        return authService.authorize(Long.parseLong(id.trim()));
    }
}
