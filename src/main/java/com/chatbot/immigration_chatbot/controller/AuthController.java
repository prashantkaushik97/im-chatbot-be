package com.chatbot.immigration_chatbot.controller;

import com.chatbot.immigration_chatbot.dto.LoginRequest;
import com.chatbot.immigration_chatbot.dto.SignUpRequest;
import com.chatbot.immigration_chatbot.dto.UserResponse;
import com.chatbot.immigration_chatbot.model.User;
import com.chatbot.immigration_chatbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public UserResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword()
        );
        User createdUser = userService.signUp(user);
        String token = userService.generateTokenForUser(createdUser);
        return new UserResponse(
                createdUser.getId(),
                createdUser.getUsername(),
                createdUser.getEmail(),
                token  // Include token in the response
        );
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest loginRequest) {
        User user = userService.authenticateAndGenerateToken(loginRequest.getEmail(), loginRequest.getPassword());
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getToken()
        );
    }
}


