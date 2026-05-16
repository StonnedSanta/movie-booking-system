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

## API Endpoints

| Method | Endpoint       | Description           |
|--------|----------------|----------------------|
| POST   | /movies        | Add a new movie       |
| GET    | /movies        | Get all movies        |
| POST   | /shows         | Add a new show        |
| GET    | /shows         | Get all shows         |
| POST   | /bookings      | Create a booking      |
| GET    | /bookings      | Get all bookings      |

---

## Clone Repository

```bash
git clone https://github.com/StonnedSanta/QMovi.git
cd QMovi
```
---

## Database Setup

1. Install PostgreSQL

2. Create database:

```sql
CREATE DATABASE qmovi;
```

3. Create PostgreSQL user:

```sql
CREATE USER qmovi_user WITH ENCRYPTED PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE qmovi TO qmovi_user;
```

4. Copy:

```plaintext
application-example.properties
```

to:

```plaintext
application.properties
```

5. Update credentials inside `application.properties`:

```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```
---

## Run Application

```bash
./gradlew bootRun
```
---

## Swagger API Documentation

After starting the app:

http://localhost:8080/swagger-ui/index.html