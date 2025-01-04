package com.chatbot.immigration_chatbot.controller;

import com.chatbot.immigration_chatbot.dto.ChatRequest;
import com.chatbot.immigration_chatbot.dto.ChatResponse;
import com.chatbot.immigration_chatbot.model.Chat;
import com.chatbot.immigration_chatbot.service.ChatService;
import com.chatbot.immigration_chatbot.service.OpenAIService;
import com.chatbot.immigration_chatbot.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/ask")
    public ResponseEntity<?> askQuestion(@RequestHeader("Authorization") String authHeader, @RequestBody ChatRequest request) {
        String sessionId = authHeader.substring(7); // Extract token, skipping "Bearer "

//
//        if (!jwtUtil.validateToken(token)) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new ErrorResponse("Invalid JWT token", "Unauthorized access"));
//        }

        String userEmail = request.getUserEmail();
        String answer = openAIService.getAnswerFromOpenAI(request.getQuestion());

        Optional<Chat> optionalChat = chatService.findChatBySessionId(sessionId);
        Chat chat = optionalChat.orElseGet(() -> new Chat()); // Use existing chat or create a new one

        if (!optionalChat.isPresent()) { // If the chat is new, initialize required fields
            chat.setSessionId(sessionId);
            chat.setUserEmail(userEmail);
            chat.setTimestamp(LocalDateTime.now());
            chat.setEntries(new ArrayList<>());
        }

        Chat.ChatEntry entry = new Chat.ChatEntry();
        entry.setQuestion(request.getQuestion());
        entry.setAnswer(answer);

        chat.getEntries().add(entry); // Add new entry to the chat

        chatService.saveChat(chat); // Save or update the chat in the database

        ChatResponse response = new ChatResponse();
        response.setAnswer(answer);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/chats")
    public ResponseEntity<?> getChatsBySessionId(@RequestHeader("Authorization") String authHeader) {
        String sessionId = authHeader.substring(7); // Extract sessionId, skipping "Bearer "

//        if (!jwtUtil.validateToken(sessionId)) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new ErrorResponse("Invalid JWT token", "Unauthorized access"));
//        }

//        List<Chat> chats = chatService.findChatBySessionId(sessionId);
        Optional<Chat> chats = chatService.findChatBySessionId(sessionId);
        if (chats.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(chats);
    }
//
//    @GetMapping("/chats/{userEmail}")
//    public List<Chat> getUserChats(@PathVariable String userEmail) {
//        List<Chat> allChats = chatService.getChatsByUserEmail(userEmail);
//        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
//        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);
//
//        List<Chat> todaysChats = new ArrayList<>();
//        for (Chat chat : allChats) {
//            if (chat.getTimestamp().isAfter(startOfDay) && chat.getTimestamp().isBefore(endOfDay)) {
//                todaysChats.add(chat);
//            }
//        }
//        return todaysChats;
//    }

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
