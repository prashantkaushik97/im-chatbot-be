package com.chatbot.immigration_chatbot.service;

import com.chatbot.immigration_chatbot.model.Session;
import com.chatbot.immigration_chatbot.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    // Create a new session
    public Session createSession(String sessionId, String userEmail, LocalDateTime startTime) {
        Session session = new Session(sessionId, userEmail, startTime);
        return sessionRepository.save(session);
    }

    // Find a session by sessionId
    public Optional<Session> getSessionById(String sessionId) {
        return sessionRepository.findBySessionId(sessionId);
    }

    // Find a session by userEmail
    public Optional<Session> getSessionByUserEmail(String userEmail) {
        return sessionRepository.findByUserEmail(userEmail);
    }
    public Optional<Session> getMostRecentSession(String userEmail) {
        return sessionRepository.findTopByUserEmailOrderByStartTimeDesc(userEmail);
    }
    // Update the end time of a session
    public Session endSession(String sessionId, LocalDateTime endTime) {
        Optional<Session> sessionOpt = sessionRepository.findBySessionId(sessionId);
        if (sessionOpt.isPresent()) {
            Session session = sessionOpt.get();
            session.setEndTime(endTime);
            return sessionRepository.save(session);
        }
        return null;
    }

    // Delete a session by sessionId
    public void deleteSession(String sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
