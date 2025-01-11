package com.chatbot.immigration_chatbot.repository;

import com.chatbot.immigration_chatbot.model.Session;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface SessionRepository extends MongoRepository<Session, String> {
    Optional<Session> findBySessionId(String sessionId);
    Optional<Session> findByUserEmail(String userEmail);
    Optional<Session> findTopByUserEmailOrderByStartTimeDesc(String userEmail);


}
