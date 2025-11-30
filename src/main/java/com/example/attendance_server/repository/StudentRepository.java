package com.example.attendance_server.repository;

import com.example.attendance_server.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {

    Student findByRoll(String roll);

}