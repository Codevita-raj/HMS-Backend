# 🏥 HMS — Hospital Management System (Backend)

A robust **Spring Boot REST API** for a Hospital Management System. Built with Java, it provides a reliable backend for managing hospital operations including patients, doctors, appointments, and administrative workflows.

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot |
| ORM | Spring Data JPA (Hibernate) |
| Database | MySQL / H2 (embedded) |
| Security | Spring Security + JWT |
| Build Tool | Maven |
| IDE Config | IntelliJ IDEA (`.idea` included) |

> **Languages:** Java (99.3%), HTML (0.7%)

---

## ✨ Features

- **Patient Management** — Register, view, update, and discharge patient records
- **Doctor Management** — Manage doctor profiles, specializations, and availability
- **Appointment Scheduling** — Book, reschedule, and cancel appointments
- **Admin Dashboard Support** — Role-based access for Admin, Doctor, and Patient roles
- **JWT Authentication** — Secure stateless session management
- **RESTful API** — Clean, resource-oriented endpoints for frontend integration

---

## 📋 Prerequisites

- **Java 17+** (or the version specified in `pom.xml`)
- **Maven 3.6+**
- **MySQL 8** running locally (or configure H2 for in-memory dev)
- **IntelliJ IDEA** (recommended — `.idea` workspace is included)

---

## ⚙️ Configuration

Update `src/main/resources/application.properties`:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/hms_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
app.jwt.secret=your_jwt_secret_key
app.jwt.expiration-ms=86400000
```

> Create the database manually before first run:
> ```sql
> CREATE DATABASE hms_db;
> ```

---

## 🏃 Running Locally

### 1. Clone the Repository

```bash
git clone https://github.com/Codevita-raj/HMS-Backend.git
cd HMS-Backend/hms
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The API will start at **http://localhost:8080**

---

## 🏗️ Project Structure

```
HMS-Backend/
├── .idea/                          # IntelliJ IDEA project files
└── hms/                            # Main Spring Boot project
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/hms/
    │   │   │       ├── config/         # Security & application config
    │   │   │       ├── controller/     # REST API controllers
    │   │   │       ├── dto/            # Request/Response DTOs
    │   │   │       ├── entity/         # JPA entities
    │   │   │       ├── exception/      # Global exception handling
    │   │   │       ├── repository/     # Spring Data repositories
    │   │   │       └── service/        # Business logic layer
    │   │   └── resources/
    │   │       └── application.properties
    │   └── test/
    └── pom.xml
```

---

## 🔐 Authentication Flow

1. **Register** — `POST /api/auth/register` with user details and role (ADMIN / DOCTOR / PATIENT)
2. **Login** — `POST /api/auth/login` returns a JWT access token
3. **Use the token** — Pass `Authorization: Bearer <token>` on all protected requests

---

## 📡 API Endpoints (Overview)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT |
| GET | `/api/patients` | List all patients (Admin) |
| POST | `/api/patients` | Register a new patient |
| GET | `/api/doctors` | List all doctors |
| POST | `/api/appointments` | Book an appointment |
| GET | `/api/appointments/{id}` | Get appointment details |
| PUT | `/api/appointments/{id}` | Update appointment |
| DELETE | `/api/appointments/{id}` | Cancel appointment |

---

## 🔗 Frontend Integration

This backend is designed to work with the **HMS Frontend**:

> 🔗 [HMS-Frontend](https://github.com/Codevita-raj/HMS-Frontend) — Angular 20 SPA for the Hospital Management System

Make sure the backend is running at `http://localhost:8080` before launching the frontend.

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit your changes: `git commit -m 'Add feature'`
4. Push and open a Pull Request

---

## 👤 Author

**Codevita-raj** — [@Codevita-raj](https://github.com/Codevita-raj)

---

## 📄 License

This project is open source. See the repository for license details.
