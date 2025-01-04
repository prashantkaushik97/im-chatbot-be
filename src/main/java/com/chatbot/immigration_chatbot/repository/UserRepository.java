package com.chatbot.immigration_chatbot.repository;

import com.chatbot.immigration_chatbot.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email); // Find user by email
    boolean existsByEmail(String email); // Check if email is already registered
}
