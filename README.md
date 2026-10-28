# Loan Eligibility Microservice

A full-stack app that checks whether a loan request is approved or rejected.

**Rule:** a loan is **Approved** if the salary is *strictly greater than* 30% of the requested amount. Otherwise it is **Rejected** (exactly 30% is Rejected).

## Tech Stack
- **Backend:** Java 17, Spring Boot 3, Spring Security, Spring Data JPA
- **Database:** MySQL
- **Frontend:** React (Vite) + Tailwind CSS
- **Testing:** JUnit 5, Mockito, MockMvc
- **Tools:** Swagger (springdoc), Docker

## Features
- `POST /api/loan/check` returns `{"decision": "Approved"}` or `{"decision": "Rejected"}`
- Input validation with clean JSON error messages (400)
- HTTP Basic authentication (401 without login)
- Every check is saved in MySQL
- Logs the decision without writing salary data
- 9 automated tests
- Swagger UI at `/swagger-ui.html`

## How It Works
```
React form -> POST /api/loan/check -> Controller -> Service (30% rule) -> MySQL
```

## Project Structure
```
backend/    Spring Boot API
frontend/   React app
```

## Run Locally

### 1. Database
Create the database in MySQL:
```sql
CREATE DATABASE loandb;
```

### 2. Backend
Set your MySQL password, then start the app.

**Windows (PowerShell):**
```powershell
cd backend
$env:DB_PASSWORD = "yourpassword"
./mvnw spring-boot:run
```

**Mac / Linux:**
```bash
cd backend
export DB_PASSWORD=yourpassword
./mvnw spring-boot:run
```
Backend runs at `http://localhost:8080`.

### 3. Frontend
```bash
cd frontend
npm install
npm run dev
```
Frontend runs at `http://localhost:5173`.

## API Example
```bash
curl -X POST http://localhost:8080/api/loan/check \
  -u officer:officer123 \
  -H "Content-Type: application/json" \
  -d '{"salary": 40000, "requestedAmount": 100000}'
```

## Run Tests
```bash
cd backend
./mvnw test
```
Tests use an in-memory H2 database, so MySQL is not needed.

## Known Limitations (what I would improve)
- The demo login (`officer` / `officer123`) is hardcoded. In production I would use JWT or OAuth2 and a secrets manager.
- The frontend contains the demo credentials. A real app would use a login page.
- Salary and amount are stored as plain values. A real bank would encrypt sensitive data.
