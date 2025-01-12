package com.chatbot.immigration_chatbot.service;

import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class OpenAIService {

    private final OpenAiService openAiService;

    public OpenAIService(@Value("${openai.api.key}") String apiKey) {
        this.openAiService = new OpenAiService(apiKey, Duration.ofSeconds(100));
    }

public String getAnswerFromOpenAI(String question) {
    // Create the user message
    ChatMessage userMessage = new ChatMessage();
    userMessage.setRole("user");
    userMessage.setContent("Answer in 200 words and use HTML tags for text formatting. Answer only if the question is related to immigration or travel. If question is off topic, tell user to ask immigration related questions politely"+ question);

    // Estimate response length needed for the model to provide a concise yet comprehensive answer
    int estimatedResponseLength = calculateOptimalResponseLength(question);

    // Build the chat completion request
    ChatCompletionRequest chatRequest = ChatCompletionRequest.builder()
            .model("gpt-4o-mini") // Using the specified model
            .messages(Collections.singletonList(userMessage)) // Include the user's message
            .maxTokens(estimatedResponseLength) // Dynamically adjust max tokens based on the input
            .temperature(0.7) // Slightly creative responses
            .build();

//    // Execute the request and get the response
    String response = openAiService.createChatCompletion(chatRequest)
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();

        return response.trim(); // Trim any extra spaces
}

    private int calculateOptimalResponseLength(String question) {
        // Basic heuristic to determine response length: 250 tokens for the question, allocate the rest for the answer
        int maxQuestionLength = 250;
        int lengthOfQuestion = Math.min(maxQuestionLength, question.length() / 4); // Roughly estimate token count from character count
        return 500 - lengthOfQuestion;
    }

}
