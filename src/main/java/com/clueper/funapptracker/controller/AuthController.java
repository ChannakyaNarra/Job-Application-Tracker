package com.clueper.funapptracker.controller;

import com.clueper.funapptracker.dto.AuthResponseDTO;
import com.clueper.funapptracker.dto.LoginRequestDTO;
import com.clueper.funapptracker.dto.RegisterRequestDTO;
import com.clueper.funapptracker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO registerRequest) {
        try {
            authService.registerUser(registerRequest);
            return new ResponseEntity<>(new AuthResponseDTO("User registered successfully!"), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new AuthResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        try {
            String token = authService.loginUser(loginRequest);
            return ResponseEntity.ok(new AuthResponseDTO("User logged in successfully!", token));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new AuthResponseDTO("Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }
    }
}
