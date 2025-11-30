package com.example.attendance_server.controller;

import com.example.attendance_server.entity.User;
import com.example.attendance_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // -------- LOGIN API --------
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {

        User user = userRepository.findByUsername( username);

        if (user != null) {
            return "success";
        } else {
            return "invalid";
        }
    }
}
