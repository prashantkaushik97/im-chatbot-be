package com.chatbot.immigration_chatbot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "sessions")
public class Session {

    @Id
    private String sessionId;  // Unique identifier for the session (JWT)
    private String userEmail;  // User's email extracted from JWT
    private LocalDateTime startTime;  // Start time of the session
    private LocalDateTime endTime;  // End time of the session (can be set on session close)

    // Constructor
    public Session(String sessionId, String userEmail, LocalDateTime startTime) {
        this.sessionId = sessionId;
        this.userEmail = userEmail;
        this.startTime = startTime;
    }

    // Getters and Setters
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
