# Contactly — Contact Management System

A secure full-stack contact manager created for the 10P Shine Java Fullstack internship assignment. Users can register, sign in with email or phone, and maintain a private, searchable contact book.

## Features

- BCrypt password hashing and stateless JWT authentication
- User-specific data isolation: users access only their own contacts
- Contact create, view, edit and delete with confirmation
- First/last name, title, multiple emails and multiple phone numbers
- Search by name, title, email or phone and server-side pagination
- Profile editing and password change
- Responsive React UI with loading, empty, validation and error states
- Global exception handling and SLF4J/Logback activity logging
- JUnit, Mockito and Vitest tests; ESLint and SonarQube configuration
- SQL Server production profile and H2 local-development profile

## Stack

| Layer | Technology |
| --- | --- |
| Backend | Java 21, Spring Boot 3, Spring Security, Spring Data JPA, Hibernate |
| Authentication | JWT (JJWT), BCrypt |
| Database | Microsoft SQL Server; H2 for local development |
| Frontend | React, Vite, Lucide React, CSS |
| Testing | JUnit 5, Mockito, Vitest, Testing Library |

## Architecture

```text
React UI → JSON/HTTP + JWT → Spring controllers → services → JPA/Hibernate → SQL Server
```

The browser never talks directly to the database. The JWT filter verifies the login token and resolves the current user. Every contact query includes that user's ID, preventing one account from accessing another account's contacts.

## Run locally

Requirements: JDK 21+, Node.js 20+, Maven 3.9+.

Backend:

```bash
cd backend
mvn spring-boot:run
```

The default `dev` profile runs at `http://localhost:8080` and creates an H2 database under `backend/data`, so SQL Server is not needed for local review.

Frontend (another terminal):

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:5173`.

## SQL Server production profile

Create database `contact_manager`, set `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, and a long random `JWT_SECRET`, then run:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

Secrets must be environment variables and must never be committed.

## API

| Method | Endpoint | Purpose |
| --- | --- | --- |
| POST | `/api/auth/register` | Register and receive JWT |
| POST | `/api/auth/login` | Login by email or phone |
| GET/POST | `/api/contacts` | Search/list or create contacts |
| GET/PUT/DELETE | `/api/contacts/{id}` | View, update or delete contact |
| GET/PUT | `/api/profile` | Read or update profile |
| PUT | `/api/profile/password` | Change password |

Protected endpoints require `Authorization: Bearer <token>`.

## Tests and quality

```bash
cd backend && mvn test
cd ../frontend && npm run build && npm test && npm run lint
```

## Real-world use

This design can power a freelancer client directory, sales leads, recruitment contacts, or a supplier directory. In production, the React build is hosted behind HTTPS, Spring Boot runs on an application server, and SQL Server stores persistent data. Each staff member signs in and sees only their own records.

## Submission workflow

Commit verified changes on a feature branch, push the branch to your personal fork, and open a pull request toward the assignment repository's `develop` branch. Never commit secrets, database files, or build output.
