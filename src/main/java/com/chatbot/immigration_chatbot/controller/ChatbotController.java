package com.chatbot.immigration_chatbot.controller;

import com.chatbot.immigration_chatbot.dto.ChatRequest;
import com.chatbot.immigration_chatbot.dto.ChatResponse;
import com.chatbot.immigration_chatbot.model.Chat;
import com.chatbot.immigration_chatbot.service.ChatService;
import com.chatbot.immigration_chatbot.service.SessionService;
import com.chatbot.immigration_chatbot.service.OpenAIService;
import com.chatbot.immigration_chatbot.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chatbot.immigration_chatbot.model.Session;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/ask")
    public ResponseEntity<?> askQuestion(@RequestHeader("Authorization") String authHeader, @RequestBody ChatRequest request) {
        String userEmail = request.getUserEmail();
        String answer = openAIService.getAnswerFromOpenAI(request.getQuestion());

        // Get the most recent session for the user
        Optional<Session> session = sessionService.getMostRecentSession(userEmail);

        // If there is an existing session and it has not ended
        if (session.isPresent() && (session.get().getEndTime() == null || session.get().getEndTime().isAfter(LocalDateTime.now()))) {
            String sessionId = session.get().getSessionId();

            // Try to find the chat by sessionId
            Optional<Chat> optionalChat = chatService.findChatBySessionId(sessionId);
            Chat chat = optionalChat.orElseGet(() -> {
                Chat newChat = new Chat();
                newChat.setSessionId(sessionId);
                return newChat;
            });

            // Add the new chat entry to the existing chat
            chat.setUserEmail(userEmail);
            chat.setTimestamp(LocalDateTime.now());
            Chat.ChatEntry entry = new Chat.ChatEntry();
            entry.setQuestion(request.getQuestion());
            entry.setAnswer(answer);
            chat.getEntries().add(entry);

            // Save the chat
            chatService.saveChat(chat);

        } else {
            // No active session found, create a new session and a new chat
            String sessionId = authHeader.substring(7); // Extract token, skipping "Bearer "
            Chat chat = new Chat();
            chat.setSessionId(sessionId);
            chat.setUserEmail(userEmail);
            chat.setTimestamp(LocalDateTime.now());
            chat.setEntries(new ArrayList<>());

            // Create a new chat entry
            Chat.ChatEntry entry = new Chat.ChatEntry();
            entry.setQuestion(request.getQuestion());
            entry.setAnswer(answer);
            chat.getEntries().add(entry);

            // Save the new chat
            chatService.saveChat(chat);

            // Return response for a new session
//            return ResponseEntity.status(HttpStatus.CREATED).body("New session created with sessionId: " + sessionId);
            // Create the new session
            sessionService.createSession(sessionId, userEmail, LocalDateTime.now());
        }
        //         Return response after processing the question
//        return ResponseEntity.ok("Question answered successfully");
        ChatResponse response = new ChatResponse();
        response.setAnswer(answer);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/chats")
    public ResponseEntity<?> getChatsBySessionId(@RequestHeader("Authorization") String authHeader,
                                                 @RequestParam("userEmail") String userEmail)
    {
//        String userEmail = request.getUserEmail();
        Optional<Session> session = sessionService.getMostRecentSession(userEmail);

        // Check if the session exists and is still active (end time is null or after the current time)
        if (session.isEmpty() || (session.get().getEndTime() != null && session.get().getEndTime().isBefore(LocalDateTime.now()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No active session found for the user or session has expired.");
        }

        // If session is active, fetch the chats associated with the sessionId
        String sessionId = session.get().getSessionId();
        Optional<Chat> chats = chatService.findChatBySessionId(sessionId);

        // Check if chats are found
        if (chats.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(chats.get());
    }
    // Error response class
    static class ErrorResponse {
        private String errorMessage;
        private String errorDetails;

        public ErrorResponse(String errorMessage, String errorDetails) {
            this.errorMessage = errorMessage;
            this.errorDetails = errorDetails;
        }

        // Getters and setters for errorMessage and errorDetails
        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorDetails() {
            return errorDetails;
        }

        public void setErrorDetails(String errorDetails) {
            this.errorDetails = errorDetails;
        }
    }
}
