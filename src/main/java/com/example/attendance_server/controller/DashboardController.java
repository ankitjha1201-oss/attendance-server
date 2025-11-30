package com.example.attendance_server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {

        // TODO: Replace with real counts from database
        Map<String, Object> stats = Map.of(
                "totalStudents", 20,
                "totalAttendanceToday", 10,
                "totalSubjects", 5
        );

        return ResponseEntity.ok(stats);
    }
}