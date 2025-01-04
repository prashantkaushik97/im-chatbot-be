package com.chatbot.immigration_chatbot.workflow;

import io.temporal.workflow.Workflow;

public class ImmigrationChatWorkflowImpl implements ImmigrationChatWorkflow {

    @Override
    public String askQuestion(String question) {
        // Simulate workflow processing
        Workflow.sleep(1000);
        return "Processed question: " + question;
    }
}

