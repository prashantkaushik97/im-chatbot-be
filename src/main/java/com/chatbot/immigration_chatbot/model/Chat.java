package com.chatbot.immigration_chatbot.model;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

public class Chat {
    @Id
    private String sessionId;  // Session ID from JWT
    private String userEmail;  // User email from JWT
    private LocalDateTime timestamp;  // Consider usage based on session start or entry-specific
    private List<ChatEntry> entries;

    // Getters and Setters for Session ID
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    // Getters and Setters for User Email
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    // Existing Getters and Setters for Timestamp
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<ChatEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ChatEntry> entries) {
        this.entries = entries;
    }

    // Inner Class for Chat Entries
    public static class ChatEntry {
        private String question;
        private String answer;
        private LocalDateTime messageTimestamp; // Optional: if detailed timestamp per entry is needed

        // Getters and Seters
        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public LocalDateTime getMessageTimestamp() {
            return messageTimestamp;
        }

        public void setMessageTimestamp(LocalDateTime messageTimestamp) {
            this.messageTimestamp = messageTimestamp;
        }
    }
}
