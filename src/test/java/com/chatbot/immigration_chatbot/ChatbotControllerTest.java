package com.chatbot.immigration_chatbot;

import com.chatbot.immigration_chatbot.controller.ChatbotController;
import com.chatbot.immigration_chatbot.dto.ChatRequest;
import com.chatbot.immigration_chatbot.dto.ChatResponse;
import com.chatbot.immigration_chatbot.model.Chat;
import com.chatbot.immigration_chatbot.model.Session;
import com.chatbot.immigration_chatbot.service.ChatService;
import com.chatbot.immigration_chatbot.service.SessionService;
import com.chatbot.immigration_chatbot.service.OpenAIService;
;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.HttpStatus.*;

import com.chatbot.immigration_chatbot.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

class ChatbotControllerTest {

    @Mock
    private ChatService chatService;

    @Mock
    private SessionService sessionService;

    @Mock
    private OpenAIService openAIService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private ChatbotController controller;

    @BeforeEach
    void setup() {
        // Initialize mocks before each test
        initMocks(this);
    }

    @Test
    void testAskQuestion_NewSession() {
        // Setup
        String authHeader = "Bearer validToken";
        String userEmail = "test@example.com";
        String question = "How to apply for a visa?";
        ChatRequest request = new ChatRequest();
        request.setUserEmail("test@example.com");
        request.setQuestion("What is the status of my application?");
        when(openAIService.getAnswerFromOpenAI(question)).thenReturn("Sample answer");
        when(sessionService.getMostRecentSession(userEmail)).thenReturn(Optional.empty());

        // Execute
        ResponseEntity<?> response = controller.askQuestion(authHeader, request);

        // Verify and Assert
        verify(sessionService, times(1)).createSession(anyString(), eq(userEmail), any(LocalDateTime.class));
        assertEquals(OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof ChatResponse);
    }

    @Test
    void testAskQuestion_ExistingActiveSession() {
        // Setup
        String userEmail = "test@example.com";
        String question = "What is the status of my application?";
        ChatRequest request = new ChatRequest();
        request.setUserEmail("test@example.com");
        request.setQuestion("What is the status of my application?");
        Session session = new Session("sessionId", userEmail,LocalDateTime.now(), LocalDateTime.now().plusHours(10));
        when(sessionService.getMostRecentSession(userEmail)).thenReturn(Optional.of(session));
        when(chatService.findChatBySessionId("sessionId")).thenReturn(Optional.of(new Chat()));

        // Execute
        ResponseEntity<?> response = controller.askQuestion("Bearer validToken", request);

        // Assert
        assertEquals(OK, response.getStatusCode());
        verify(chatService, times(1)).saveChat(any(Chat.class));
    }

    @Test
    void testGetChatsBySessionId_NoActiveSession() {
        // Setup
        String userEmail = "test@example.com";
        when(sessionService.getMostRecentSession(userEmail)).thenReturn(Optional.empty());

        // Execute
        ResponseEntity<?> response = controller.getChatsBySessionId("Bearer validToken", userEmail);

        // Assert
        assertEquals(FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testGetChatsBySessionId_ActiveSessionWithChats() {
        // Setup
        String userEmail = "test@example.com";
        Session session = new Session("sessionId", userEmail, LocalDateTime.now(), LocalDateTime.now().plusHours(10));
        Chat chat = new Chat();
        when(sessionService.getMostRecentSession(userEmail)).thenReturn(Optional.of(session));
        when(chatService.findChatBySessionId("sessionId")).thenReturn(Optional.of(chat));

        // Execute
        ResponseEntity<?> response = controller.getChatsBySessionId("Bearer validToken", userEmail);

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertEquals(chat, response.getBody());
    }
}
