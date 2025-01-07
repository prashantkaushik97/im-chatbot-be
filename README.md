# im-chatbot-be

This repository contains the backend code for an immigration chatbot built with Spring Boot. The application uses Java 17, MongoDB for data storage, and integrates with the OpenAI API for processing chatbot queries.

## Prerequisites

- Java 17
- Docker
- MongoDB account
- OpenAI API key

## Setting up the environment

Before running the application, make sure to set up the Temporal server and configure all necessary environment variables.

### Running Temporal Server with Docker

To run the Temporal server, use the following Docker command:

git clone https://github.com/temporalio/docker-compose.git
cd docker-compose
docker compose up

### Configuring Environment Variables
You need to configure the following environment variables in your development environment:

SPRING_DATA_MONGODB_URI - Your MongoDB connection string.
OPENAI_API_KEY - Your API key for accessing OpenAI services.
You can set these environment variables on your machine. For Unix-like operating systems (Linux, macOS), you can add them to your .bashrc or .zshrc file:
export MONGODB_URI="your-mongodb-uri"
export OPENAI_API_KEY="your-openai-api-key"

### Running the application
To run the Spring Boot application, navigate to the root directory of the project and use the following command:


./mvnw spring-boot:run
This command uses Maven Wrapper to compile the application and start it on the default port (8081).
Usage
Once the application is running, you can interact with the immigration chatbot through the configured endpoints, available through http://localhost:8081/.


