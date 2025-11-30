package com.example.attendance_server.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Refer directly to student by roll instead of studentId
    @Column(nullable = false)
    private String roll;

    private String status; // Present / Absent
    private String subject;
    private LocalDate date;
}