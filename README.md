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