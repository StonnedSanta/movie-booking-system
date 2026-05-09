# QMovi (Movie Booking System)

A backend-focused movie booking system built using Java, JDBC, PostgreSQL, and concurrent programming concepts.  
The project simulates real-world booking scenarios with transaction handling, row-level locking, retries, and idempotent request processing.

Currently evolving into a scalable backend architecture with Spring Boot, REST APIs, monitoring, messaging queues, and distributed system concepts.

---

## Features

- Movie & Show Management
- Concurrent Seat Booking Simulation
- JDBC + PostgreSQL Integration
- Transaction Commit / Rollback
- Row-Level Locking (`FOR UPDATE SKIP LOCKED`)
- Retry Mechanism with Exponential Backoff
- Idempotent Booking Requests
- Async Processing using `CompletableFuture`

---

## Tech Stack

### Current
- Java
- JDBC
- PostgreSQL
- CompletableFuture
- SQL Transactions

### In Progress / Planned Architecture
- Spring Boot REST APIs
- Hibernate / JPA
- Swagger/OpenAPI
- Kafka
- RabbitMQ
- Prometheus
- ELK Stack
- New Relic
- OAuth2 / Spring Security
- MongoDB (for scalable logging/event storage)

---

## Project Structure

```plaintext
src/
├── app/
├── model/
├── repository/
└── service/
```

---

## Sample Output

```plaintext
User1: Booking CONFIRMED
User2: Seat already booked!
User3: Payment failed -> Booking rolled back
```

---

## Author

Anuj Srivastava