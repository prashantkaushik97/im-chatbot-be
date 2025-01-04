package com.chatbot.immigration_chatbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration class for application-specific beans.
 * This class is used to define beans that can be injected into other parts of the application.
 */
@Configuration
public class AppConfig {

    /**
     * Creates a BCryptPasswordEncoder bean.
     * This bean provides a utility for encrypting and verifying passwords using the BCrypt hashing algorithm.
     *
     * @return a new instance of BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
