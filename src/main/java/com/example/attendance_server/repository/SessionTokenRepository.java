package com.example.attendance_server.repository;

import com.example.attendance_server.entity.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionTokenRepository extends JpaRepository<SessionToken, Long> {

    SessionToken findByToken(String token);

}
