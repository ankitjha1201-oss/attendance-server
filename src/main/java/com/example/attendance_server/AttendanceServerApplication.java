package com.example.attendance_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AttendanceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceServerApplication.class, args);
    }

    @GetMapping("/")
    public String hello() {
        return "✅ Cloud Attendance Server is running!";
    }
}
