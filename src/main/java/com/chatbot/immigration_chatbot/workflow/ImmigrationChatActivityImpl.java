package com.chatbot.immigration_chatbot.workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImmigrationChatActivityImpl implements ImmigrationChatActivity {
    private static final Logger logger = LoggerFactory.getLogger(ImmigrationChatActivityImpl.class);

    @Override
    public String processAnswer(String question) {
        logger.info("Processing answer for question: {}", question);
        return "Answer for: " + question;
    }
}
