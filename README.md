# QMovi

A Spring Boot based Movie Booking backend system demonstrating:

- REST APIs
- Layered architecture (Controller → Service → Repository)
- JPA/Hibernate integration
- PostgreSQL persistence
- Swagger/OpenAPI documentation
- DTO validation
- Relational entity mapping

---

## Features

- **Movie Management APIs** (Add/Get movies)
- **Show Management APIs** (Add/Get shows)
- **Booking APIs** (Create/Get bookings)
- Global Exception Handling
- PostgreSQL database persistence
- Swagger/OpenAPI documentation
- DTO validation and relational mapping

---

## Tech Stack

- **Java 25**
- **Spring Boot 3**
- **PostgreSQL**
- **Hibernate / JPA**
- **Gradle**
- **Swagger/OpenAPI**
- **Lombok**

---

## Architecture

Controller
↓
Service
↓
Repository
↓
PostgreSQL

---

## API Endpoints

| Method | Endpoint       | Description           |
|--------|----------------|----------------------|
| POST   | /movies        | Add a new movie       |
| GET    | /movies        | Get all movies        |
| POST   | /shows         | Add a new show        |
| GET    | /shows         | Get all shows         |
| POST   | /bookings      | Create a booking      |
| GET    | /bookings      | Get all bookings      |

**Swagger UI:**  
[Open Swagger UI](http://localhost:8080/swagger-ui/index.html)

---

## Database Setup

1. Install **PostgreSQL**.
2. Create database:

```sql
CREATE DATABASE qmovi;