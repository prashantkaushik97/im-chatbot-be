package com.chatbot.immigration_chatbot.dto;

public class UserResponse {
    private final String id;
    private final String username;
    private final String email;
    private final String token; // Add token field to hold JWT token

    // Updated constructor to include the token
    public UserResponse(String id, String username, String email, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.token = token;
    }

    // Getters only for immutability
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
