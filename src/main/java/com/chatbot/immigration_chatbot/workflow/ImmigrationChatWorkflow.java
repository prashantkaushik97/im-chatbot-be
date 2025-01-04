package com.chatbot.immigration_chatbot.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ImmigrationChatWorkflow {
    @WorkflowMethod
    String askQuestion(String question);
}