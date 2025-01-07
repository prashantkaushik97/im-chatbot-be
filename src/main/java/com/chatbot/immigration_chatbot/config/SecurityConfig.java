package com.chatbot.immigration_chatbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.spec.SecretKeySpec;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.List;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * Configures the Spring Security filter chain.
     * - Enables CORS.
     * - Protects specific endpoints with authentication.
     * - Disables CSRF for stateless APIs.
     * - Configures JWT-based authentication.
     * @param http HttpSecurity object to configure security rules.
     * @return Configured SecurityFilterChain.
     * @throws Exception if configuration fails.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS and apply custom configuration
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/chatbot/ask").authenticated() // Require authentication for this endpoint
                        .anyRequest().permitAll() // Allow all other endpoints
                )
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless applications
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))); // Enable JWT authentication
        return http.build(); // Build and return the SecurityFilterChain
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing) settings.
     * - Allows requests from the frontend's origin.
     * - Enables common HTTP methods and headers.
     * - Supports credentials sharing.
     * @return UrlBasedCorsConfigurationSource with configured CORS settings.
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("http://localhost:3000", "https://im-chatbot-fe.web.app/", "http://im-chatbot-fe.web.app/"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow common HTTP methods
        corsConfig.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Allow headers for authentication and content type
        corsConfig.setAllowCredentials(true); // Enable credentials sharing

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig); // Apply CORS settings to all endpoints
        return source;
    }

    /**
     * Configures the JWT authentication converter.
     * - This is used to extract authentication details from JWT tokens.
     * @return JwtAuthenticationConverter for converting JWT claims to authentication objects.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        return new JwtAuthenticationConverter();
    }

    /**
     * Configures the JWT decoder for validating tokens.
     * - Uses a symmetric key for HMAC-SHA256 signature verification.
     * - The key is decoded from a Base64-encoded string.
     * @return Configured JwtDecoder for decoding and validating JWT tokens.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        // Base64-encoded symmetric key for HMAC-SHA256
        byte[] keyBytes = Base64.getDecoder().decode("dGhpcy1pcy1hLWR1bW15LXNlY3JldC1rZXktZm9yLXRlc3Rpbmctb25seQ==");
        SecretKeySpec key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName()); // Generate the secret key
        return NimbusJwtDecoder.withSecretKey(key).build(); // Return the configured decoder
    }
}
