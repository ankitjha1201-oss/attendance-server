package com.example.attendance_server.service;

import com.example.attendance_server.entity.SessionToken;
import com.example.attendance_server.repository.SessionTokenRepository;
import com.example.attendance_server.util.QRUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    private SessionTokenRepository sessionTokenRepository;

    /**
     * Create and persist a session token for given subject and expiry seconds.
     * Returns the saved SessionToken.
     */
    public SessionToken createSession(String subject, long expirySeconds) {
        SessionToken session = new SessionToken();
        session.setToken(UUID.randomUUID().toString());
        session.setExpiryTime(LocalDateTime.now().plusSeconds(expirySeconds));
        session.setSubject(subject);
        session.setDate(LocalDateTime.now());
        return sessionTokenRepository.save(session);
    }

    public SessionToken findByToken(String token) {
        return sessionTokenRepository.findByToken(token);
    }

    /**
     * Generate a data-uri Base64 PNG QR for the attendance URL.
     * Example: "http://your-server/attend?token=..."
     */
    public String generateQrDataUriForToken(String token, String baseAttendUrl) throws Exception {
        // build the URL embedded in QR
        String url = baseAttendUrl;
        if (!baseAttendUrl.contains("?")) {
            url = baseAttendUrl + "?token=" + java.net.URLEncoder.encode(token, "UTF-8");
        } else {
            url = baseAttendUrl + "&token=" + java.net.URLEncoder.encode(token, "UTF-8");
        }
        // create PNG and return data URI
        return QRUtil.generateQrBase64DataUri(url, 300, 300);
    }
}