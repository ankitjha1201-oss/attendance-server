package com.example.attendance_server.dto;

import com.example.attendance_server.entity.SessionToken;

public class SessionResponseDTO {
    private Long id;
    private String token;
    private String subject;
    private String qrDataUri; // data:image/png;base64,...
    private String expiryTime; // ISO string of expiry

    public SessionResponseDTO() {}

    public SessionResponseDTO(SessionToken s, String qrDataUri) {
        this.id = s.getId();
        this.token = s.getToken();
        this.subject = s.getSubject();
        this.expiryTime = s.getExpiryTime() == null ? null : s.getExpiryTime().toString();
        this.qrDataUri = qrDataUri;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getQrDataUri() { return qrDataUri; }
    public void setQrDataUri(String qrDataUri) { this.qrDataUri = qrDataUri; }

    public String getExpiryTime() { return expiryTime; }
    public void setExpiryTime(String expiryTime) { this.expiryTime = expiryTime; }
}