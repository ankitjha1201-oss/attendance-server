package com.example.attendance_server.controller;

import com.example.attendance_server.dto.SessionResponseDTO;
import com.example.attendance_server.entity.SessionToken;
import com.example.attendance_server.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
@CrossOrigin(origins = "*") // allow mobile apps to access API
public class SessionController {

    @Autowired
    private SessionService sessionService;

    /**
     * POST /api/session/generate
     * Generate a session token and a QR data-uri for attendance
     *
     * @param subject      Subject name
     * @param expirySeconds Token expiry in seconds (default 120)
     * @param attendUrl    Base URL for marking attendance (default to validate endpoint)
     * @return SessionResponseDTO with token + qrDataUri
     * @throws Exception
     */
    @PostMapping("/generate")
    public SessionResponseDTO generateToken(
            @RequestParam String subject,
            @RequestParam(required = false, defaultValue = "120") long expirySeconds,
            @RequestParam(required = false,
                    defaultValue = "http://192.168.1.35:8080/api/session/validate") String attendUrl
    ) throws Exception {
        // create session token
        SessionToken session = sessionService.createSession(subject, expirySeconds);

        // generate QR data URI pointing to validate endpoint
        String qrDataUri = sessionService.generateQrDataUriForToken(session.getToken(), attendUrl);

        return new SessionResponseDTO(session, qrDataUri);
    }

    /**
     * Optional GET endpoint for testing in browser
     */
    @GetMapping("/generate")
    public SessionResponseDTO generateTokenGet(
            @RequestParam String subject,
            @RequestParam(required = false, defaultValue = "120") long expirySeconds,
            @RequestParam(required = false,
                    defaultValue = "http://192.168.1.35:8080/api/session/validate") String attendUrl
    ) throws Exception {
        return generateToken(subject, expirySeconds, attendUrl);
    }

    /**
     * GET /api/session/validate
     * Validate if a session token is still active
     *
     * @param token Session token
     * @return true if token is valid and not expired, false otherwise
     */
    @GetMapping("/validate")
    public boolean validateToken(@RequestParam String token) {
        SessionToken session = sessionService.findByToken(token);
        if (session == null) return false;

        return java.time.LocalDateTime.now().isBefore(session.getExpiryTime());
    }
}
