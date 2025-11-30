package com.example.attendance_server.controller;

import com.example.attendance_server.entity.Student;
import com.example.attendance_server.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // -------------------------------------
    // ADD STUDENT (POST)
    // -------------------------------------
    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@RequestBody Student student) {

        if (student.getName() == null || student.getRoll() == null || student.getName().isEmpty() || student.getRoll().isEmpty()) {
            return ResponseEntity.badRequest().body("Name and Roll number are required!");
        }

        studentRepository.save(student);
        return ResponseEntity.ok("Student added successfully!");
    }

    // -------------------------------------
    // GET ALL STUDENTS (GET)
    // -------------------------------------
    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentRepository.findAll());
    }

    // -------------------------------------
    // FIND STUDENT BY ROLL NUMBER (GET)
    // Used in QR attendance & lookups
    // -------------------------------------
    @GetMapping("/find")
    public ResponseEntity<?> getStudentByRoll(@RequestParam String roll) {

        Student student = studentRepository.findByRoll(roll);

        if (student == null) {
            return ResponseEntity.status(404).body("Student Not Found");
        }

        return ResponseEntity.ok(student);
    }
}