# README for Task Management Application

## Project Overview
This is a full-stack task management application with:
- Angular frontend (run with `ng serve`)
- Spring Boot backend (running on port 8089)
- MySQL 8.0 database (using a database named "task")

## Prerequisites
Before you begin, ensure you have the following installed:
- Node.js (v14 or higher)
- Angular CLI (`npm install -g @angular/cli`)
- Java JDK (version 11 or higher)
- MySQL Server (version 8.0)
- Maven (for Spring Boot)

## Setup Instructions

### 1. Database Setup (MySQL 8.0)
1. Start your MySQL 8.0 server
2. Create a database named `task`:
   ```sql
   CREATE DATABASE task;
   ```
3. Update the Spring Boot application properties with your MySQL 8.0 credentials:
   - File: `src/main/resources/application.properties`
   - Example configuration:
     ```
     spring.datasource.url=jdbc:mysql://localhost:3306/task?useSSL=false&serverTimezone=UTC
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     ```
   - Note the additional parameters for MySQL 8.0 compatibility

### 2. Backend Setup
1. Navigate to the Spring Boot project directory
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   - The backend will start on `http://localhost:8089`

### 3. Frontend Setup
1. Navigate to the Angular project directory
2. Install dependencies:
   ```bash
   npm install
   ```
3. Configure the API base URL in `src/environments/environment.ts`:
   ```typescript
   export const environment = {
     production: false,
     apiUrl: 'http://localhost:8089/api/tasks'
   };
   ```
4. Run the development server:
   ```bash
   ng serve
   ```
   - The frontend will start on `http://localhost:4200`

## API Endpoints
The Spring Boot application provides the following endpoints:

### Task Management
- `POST /api/tasks` - Add a new task
  - Request body: TaskDTO
  - Returns: Created TaskDTO with HTTP 201 status

- `GET /api/tasks` - Get recent tasks
  - Returns: List of TaskDTO objects

- `PUT /api/tasks/{taskId}/complete` - Mark a task as completed
  - Returns: Completed TaskDTO

## Configuration
- Frontend API base URL is configured in `src/environments/environment.ts`
- Backend server port is set to 8089 in `application.properties`
- MySQL 8.0 specific JDBC URL parameters are included for proper connectivity

## Troubleshooting
- **MySQL 8.0 Connection Issues**:
  - Ensure you're using the correct JDBC URL with timezone setting
  - Verify the MySQL 8.0 driver is in your dependencies
  - Check that the user has proper permissions


- **Port Conflicts**:
  - Verify nothing else is using port 8089
  - Change port in `application.properties` if needed:
    ```
    server.port=8089
    ```

## Development Notes
- The backend uses:
  - Spring Data JPA for database operations
  - DTO pattern for API responses
  - Validation annotations for request bodies
- The frontend should use Angular services to call these endpoints
- MySQL 8.0 requires specific connection parameters for proper timezone handling
