# README for Dockerized Full-Stack Application

## Project Overview
This project consists of a full-stack application with:
- Frontend application
- Spring Boot backend application
- MySQL database

All components are orchestrated using Docker Compose for easy setup and deployment.

## Prerequisites
Before you begin, ensure you have the following installed:
- Docker (version 20.10.0 or higher)
- Docker Compose (version 1.29.0 or higher)
- Git (optional, for cloning the repository)

## Getting Started

### 1. Clone the Repository (if applicable)
```bash
git clone https://github.com/ChamaraPriyadarshanaCode/CoverageX.git
cd CoverageX
git checkout master
```

### 2. Build and Run with Docker Compose
Run the following command from the project root directory:

```bash
docker-compose up --build
```

For detached mode (running in background):
```bash
docker-compose up --build -d
```

### 3. Access the Applications
- **Frontend**: http://localhost:80 (port may vary based on your configuration)
- **Backend API**: http://localhost:8089
- **MySQL Database**: Accessible internally at `mysql:3306` from other containers

## Available Services
The Docker Compose file defines the following services:
1. `frontend`: The frontend application
2. `backend`: Spring Boot application
3. `mysql`: MySQL database service

## Useful Commands

### Docker Compose Commands
- Start services in detached mode: `docker-compose up -d`
- Stop services: `docker-compose down`
- View running containers: `docker-compose ps`

### Cleanup
To stop and remove all containers, networks, and volumes:
```bash
docker-compose down -v
