package com.chatbot.immigration_chatbot.service;

import com.chatbot.immigration_chatbot.model.Chat;
import com.chatbot.immigration_chatbot.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;


@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public void saveChat(Chat chat) {
        chatRepository.save(chat);
    }

    public List<Chat> getChatsByUserEmail(String userEmail) {
        return chatRepository.findByUserEmail(userEmail);
    }

    public Optional<Chat> findChatBySessionId(String sessionId) {
        return chatRepository.findBySessionId(sessionId);
    }
}
