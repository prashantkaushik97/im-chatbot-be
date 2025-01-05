//package com.chatbot.immigration_chatbot.config;
//
//import com.chatbot.immigration_chatbot.workflow.ImmigrationChatWorkflowImpl;
//import com.chatbot.immigration_chatbot.workflow.ImmigrationChatActivityImpl;
//
//import io.temporal.client.WorkflowClient;
//import io.temporal.serviceclient.WorkflowServiceStubs;
//import io.temporal.worker.Worker;
//import io.temporal.worker.WorkerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Configuration class for Temporal workflow setup.
// * This class defines the beans required for Temporal workflow and worker creation.
// */
//@Configuration
//public class TemporalConfig {
//
//    /**
//     * Creates and configures the WorkflowServiceStubs bean.
//     * WorkflowServiceStubs acts as a client for connecting to the Temporal service.
//     *
//     * @return a new instance of WorkflowServiceStubs.
//     */
//    @Bean
//    public WorkflowServiceStubs workflowServiceStubs() {
//        return WorkflowServiceStubs.newInstance();
//    }
//
//    /**
//     * Creates a WorkflowClient bean for communicating with Temporal service.
//     * The client is used to start workflows and interact with running workflows.
//     *
//     * @param service an instance of WorkflowServiceStubs.
//     * @return a new instance of WorkflowClient.
//     */
//    @Bean
//    public WorkflowClient workflowClient(WorkflowServiceStubs service) {
//        return WorkflowClient.newInstance(service);
//    }
//
//    /**
//     * Creates a WorkerFactory bean responsible for creating and managing workers.
//     * A WorkerFactory is used to register workers and start them.
//     *
//     * @param client an instance of WorkflowClient.
//     * @return a new instance of WorkerFactory.
//     */
//    @Bean
//    public WorkerFactory workerFactory(WorkflowClient client) {
//        return WorkerFactory.newInstance(client);
//    }
//
//    /**
//     * Creates a Worker bean for processing tasks from a specific task queue.
//     * Registers workflow and activity implementations with the worker and starts the factory.
//     *
//     * @param factory an instance of WorkerFactory.
//     * @return a configured Worker instance.
//     */
//    @Bean
//    public Worker workflowWorker(WorkerFactory factory) {
//        // Create a worker to listen to the specified task queue
//        Worker worker = factory.newWorker("IMMIGRATION_CHAT_TASK_QUEUE");
//
//        // Register workflow implementation types with the worker
//        worker.registerWorkflowImplementationTypes(ImmigrationChatWorkflowImpl.class);
//
//        // Register activity implementation instances with the worker
//        worker.registerActivitiesImplementations(new ImmigrationChatActivityImpl());
//
//        // Start the worker factory to begin polling the task queue
//        factory.start();
//
//        return worker;
//    }
//}
