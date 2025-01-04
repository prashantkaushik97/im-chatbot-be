package com.chatbot.immigration_chatbot.dto;

//import javax.validation.constraints.NotBlank;

public class ChatRequest {

//    @NotBlank(message = "Question cannot be blank")
    private String question;

//    @NotBlank(message = "User ID cannot be blank")
    private String userEmail;

    // Getters and Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
