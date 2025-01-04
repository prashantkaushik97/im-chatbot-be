package com.chatbot.immigration_chatbot.repository;

import com.chatbot.immigration_chatbot.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;
public interface ChatRepository extends MongoRepository<Chat, String> {  // Assuming Chat ID is a String type
    List<Chat> findByUserEmail(String userEmail);
    Optional<Chat> findBySessionId(String sessionId);  // Method to find a chat by session ID
}
