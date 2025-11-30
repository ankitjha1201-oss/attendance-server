package com.example.attendance_server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        // Hardcoded login for now (later will connect to database)
        if ("admin".equals(request.getUsername()) &&
                "1234".equals(request.getPassword())) {

            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }
    }
}