package com.example.attendance_server.controller;

import com.example.attendance_server.entity.Attendance;
import com.example.attendance_server.entity.SessionToken;
import com.example.attendance_server.entity.Student;
import com.example.attendance_server.repository.AttendanceRepository;
import com.example.attendance_server.repository.SessionTokenRepository;
import com.example.attendance_server.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private SessionTokenRepository sessionTokenRepository;

    @Autowired
    private StudentRepository studentRepository;

    // ----------------------------------------------------
    // 1) Manual attendance (POST JSON)
    // ----------------------------------------------------
    @PostMapping
    public ResponseEntity<?> markAttendance(@RequestBody Attendance attendance) {

        if (attendance.getRoll() == null || attendance.getRoll().isEmpty()) {
            return ResponseEntity.badRequest().body("Roll number is required");
        }

        // Set today's date
        attendance.setDate(LocalDate.now());

        // Prevent duplicate (same roll + date + subject)
        List<Attendance> duplicates =
                attendanceRepository.findByRollAndDate(attendance.getRoll(), attendance.getDate());

        boolean duplicateFound =
                duplicates.stream().anyMatch(a -> a.getSubject().equals(attendance.getSubject()));

        if (duplicateFound) {
            return ResponseEntity.status(409).body("Attendance already marked today");
        }

        Attendance saved = attendanceRepository.save(attendance);
        return ResponseEntity.ok(saved);
    }


    // ----------------------------------------------------
    // 2) QR Attendance
    // ----------------------------------------------------
    @PostMapping("/mark-via-qr")
    public ResponseEntity<?> markAttendanceViaQR(
            @RequestParam String token,
            @RequestParam String roll
    ) {

        // Validate token
        SessionToken session = sessionTokenRepository.findByToken(token);
        if (session == null) {
            return ResponseEntity.status(401).body("Invalid QR Token");
        }

        // Check expiry
        if (LocalDateTime.now().isAfter(session.getExpiryTime())) {
            return ResponseEntity.status(403).body("Token expired – scan again");
        }

        // Validate student exists
        Student student = studentRepository.findByRoll(roll);
        if (student == null) {
            return ResponseEntity.status(404).body("Student not found");
        }

        LocalDate today = LocalDate.now();

        // Check duplicate
        List<Attendance> duplicates =
                attendanceRepository.findByRollAndDate(roll, today);

        boolean duplicateFound =
                duplicates.stream().anyMatch(a -> a.getSubject().equals(session.getSubject()));

        if (duplicateFound) {
            return ResponseEntity.status(409).body("Attendance already marked!");
        }

        // Save attendance
        Attendance attendance = new Attendance();
        attendance.setRoll(roll);
        attendance.setSubject(session.getSubject());
        attendance.setDate(today);
        attendance.setStatus("Present");

        attendanceRepository.save(attendance);

        return ResponseEntity.ok("Attendance Marked Successfully");
    }


    // ----------------------------------------------------
    // Get all attendance
    // ----------------------------------------------------
    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        return ResponseEntity.ok(attendanceRepository.findAll());
    }

    // ----------------------------------------------------
    // Get attendance by date
    // ----------------------------------------------------
    @GetMapping("/{date}")
    public ResponseEntity<List<Attendance>> getAttendanceByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(attendanceRepository.findByDate(localDate));
    }
}