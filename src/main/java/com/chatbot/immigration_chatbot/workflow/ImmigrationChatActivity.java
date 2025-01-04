package com.chatbot.immigration_chatbot.workflow;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface ImmigrationChatActivity {
    String processAnswer(String question);
}
