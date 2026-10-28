# Mini Switch

A small ISO 8583 switching prototype built with Java, Spring Boot, and [jPOS](https://jpos.org/).

This project is a hands-on learning exercise focused on understanding ISO 8583 messaging and the fundamentals of a transaction-switching system.

## Features

- ISO 8583 message packing and unpacking
- TCP client-server communication using jPOS channels
- Basic transaction request validation
- Simple authorization logic with response codes
- Socket read timeout handling on the client
- Spring-managed server lifecycle

## Tech Stack

- Java 17
- Spring Boot
- jPOS
- Maven

## Getting Started

### Prerequisites

- JDK 17
- Maven

### Running the Server

Run the Spring Boot application:

```bash
mvn spring-boot:run
```

The switch listens on port `5000`.

### Running the Client

Run `MiniSwitchClient.java` from your IDE using its `main` method.

The client connects to the local server, sends an ISO 8583 transaction request, and prints the response MTI and response code.

## Disclaimer

This project is intended for educational purposes only. It is not a production-ready payment switch and does not implement real payment-network authorization, security, or financial transaction processing.
