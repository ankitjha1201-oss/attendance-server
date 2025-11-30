package com.example.attendance_server.repository;

import com.example.attendance_server.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    // Get attendance for a specific student using roll
    List<Attendance> findByRoll(String roll);

    // Count attendance on a specific date
    long countByDate(LocalDate date);

    // Get attendance for a specific date
    List<Attendance> findByDate(LocalDate date);

    // Find attendance by roll + date
    List<Attendance> findByRollAndDate(String roll, LocalDate date);

    // Check duplicate: (roll + date + subject)
    boolean existsByRollAndDateAndSubject(String roll, LocalDate date, String subject);
}
