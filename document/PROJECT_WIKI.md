# SimpleCurdApp Project Wiki

This document serves as the central context and architecture reference for the SimpleCurdApp project. It is designed to quickly provide LLMs or new developers with the necessary context to understand and work on the application.

## Overview
SimpleCurdApp is a full-stack Spring Boot REST API application with a vanilla HTML/CSS/JavaScript frontend. It provides standard CRUD (Create, Read, Update, Delete) operations, likely simulating an e-commerce platform's core product catalog based on the package namings (`testjspecomplatform`).

## Tech Stack
- **Backend:** Java, Spring Boot, Maven (wrapper included)
- **Frontend:** HTML5, CSS3, Vanilla JavaScript (`frontend/` directory)
- **Database:** Relational Database (SQL scripts included for setup like `insert_sample_data.sql` and `SimpleCurd.session.sql`)
- **Containerization:** Docker (`Dockerfile`), Docker Compose (`docker-compose.yml`)
- **Orchestration:** Kubernetes (`deployment.yaml`, `service.yaml`, `hpa.yaml`, `kuber.md`)
- **Testing:** 
  - **API Testing:** Postman (`postman-testing/`)
  - **BDD Testing:** Karate Framework (`src/test/`, `products.feature`)
- **Documentation:** Swagger UI (documented in `document/SWAGGER_DOCUMENTATION.md`)

## Project Structure
- **/src/main/**: Core Spring Boot application containing Configurations, Controllers, Exceptions, Models, Repositories, and Services implementations.
- **/src/test/**: Contains automated testing, featuring Karate BDD test configurations and test suites (`KarateTests.java`).
- **/frontend/**: Contains the static UI assets (`index.html`, `app.js`, `styles.css`) that interact with the backend API.
- **/document/**: Assorted documentation including `API_TESTING_GUIDE.md`, `SWAGGER_DOCUMENTATION.md`, and references for Kubernetes.
- **/postman-testing/**: Contains Postman collections and environments for executing manual or automated API tests.

## Deployment & Operations
- **Local Run scripts:** Provide `start-backend.sh` and `stop-backend.sh` for easy standalone execution.
- **Docker Compose:** Use `docker-compose.yml` to spin up the application dependencies and standard images locally.
- **Kubernetes:** The root contains manifest files (`deployment.yaml`, `service.yaml`, `hpa.yaml`) for seamless deployment in a K8s cluster, including scalable Horizontal Pod Autoscaler configs.

## Getting Started For LLMs
If prompted to implement a new feature, check:
1. **API Layer:** `controller/` & `service/` for business logic.
2. **Data Layer:** `repository/` & `model/` for database schemas and entities.
3. **Frontend:** Make sure to sync any REST API schema changes with `/frontend/app.js`.
4. **Testing:** Update the Karate specifications under `src/test/java/com/example/testjspecomplatform/products.feature` or Postman collections.

*Note: Share this file with the AI model or chat session primarily as project context.*