# API Test Automation Framework

Automated REST API testing framework developed using Java, RestAssured, TestNG, and Maven for testing the Swagger Petstore APIs.

---

## Overview

This framework is designed for scalable and maintainable API automation testing of the Swagger Petstore application.

Swagger URL:
https://petstore.swagger.io/

The framework supports:
- REST API testing
- CRUD operation validation
- Request/response assertions
- JSON serialization/deserialization
- Retry mechanism for flaky APIs
- Logging 
- Parallel execution

---

## Versions

| Tecnology   | Version | Purpose |
|-------------|---------|--------|
| Java        | 21 |Programming Language|
| Maven       | 3.x |Dependency Management|
| RestAssured | 5.3.0 |API Automation|
| TestNG      | 7.8.0 |Test Framework|
| Jackson     | 2.15.2 JSON Serialization/Deserialization|
| Lombok      | 1.18.30 |DataObjects Getters and Setters|
| SLF4J       | 1.7.36 |Logging |
| DotEnv      | 5.2.2 | Environment File Configuration|
| Faker       | 1.0.2 |Test Data Generation|


---


## Features

- Modular framework architecture
- Reusable API helper methods
- Centralized configuration management
- Automatic request and response logging for failed cases
- Retry mechanism for unstable APIs
- Parallel test execution support
- Detailed reporting and logs
- TestNG suite execution support

---

## Application Under Test

Swagger Petstore API:
https://petstore.swagger.io/

Modules Covered:
- User APIs
- Pet APIs
- Store APIs

---

## Project Structure

```text
src
├── main
│   ├── java
│   │   ├── datafactory
│   │   │   ├── pet
│   │   │   ├── store
│   │   │   └── user
│   │   ├── dataobjects
│   │   │   ├── pet
│   │   │   ├── store
│   │   │   └── user
│   │   ├── utils
│   │   ├── resources
│   │   │   └── uploadFiles
│
├── test
│   ├── java
│   │   ├── tests
│   │   │   ├── base
│   │   │   ├── pet
│   │   │   ├── store
│   │   │   └── user
├── .env
├── .gitignore
├── pom.xml
├── README.md
└── testng.xml
```

---

## Prerequisites

Ensure the following are installed before running the framework:

- Java 21
- Maven
- IntelliJ IDEA / Eclipse
- Git

Verify installation:

```bash
java -version
mvn -version
```

---

## Installation

Clone the repository:

```bash
git clone <repository-url>
```

Navigate to the project directory:

```bash
cd PetStoreAPIAutomationUsingAI
```

Install dependencies:

```bash
mvn clean install
```

---

## Running Tests

### Run All Tests

```bash
mvn test
```

### Run Using TestNG XML

```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Run Specific Test Class

```bash
mvn test -Dtest=UserApiTests
```

### Run Specific Test Method

```bash
mvn test -Dtest=UserApiTests#createUserTest
```

---

## Framework Components

### BaseTest

Handles:
- Framework initialization
- Common setup methods
- Request/response logging configuration

### ApiHelpers

Reusable helper methods for:
- GET
- POST
- PUT
- DELETE

Supports:
- Headers
- Query parameters
- Path parameters
- Authentication
- Retry logic
- Content type

### DataObject Models

DataObject classes are used for:
- Request payload creation
- Response deserialization

### Constants

Centralized constants for:
- Status codes
- Endpoints
- Framework configuration

### Retry Mechanism

Reusable retry utility implemented for handling intermittent API failures.

Features:
- Configurable retry count
- Configurable wait duration
- Automatic retry on failed responses



---

## Request and Response Logging

Automatic logging is enabled for :
- Request URI
- Headers
- Payload
- Response body
- Status codes

Failed validations automatically log complete request and response details for debugging.

---

## Assertions Covered

The framework validates:
- Status codes
- Response body
- JSON schema
- Headers
- Response time
- Data integrity

---

## Parallel Execution

Parallel execution is supported using TestNG configuration.

Example:

```xml
<suite name="PetStore API Suite" parallel="classes" thread-count="4">
</suite>
```

This improves execution speed during CI/CD runs.

---

## CI/CD Integration

Framework can be integrated with:
- Jenkins
- GitHub Actions
- Azure DevOps

Supports:
- Scheduled execution
- Parallel execution

---

## Best Practices Followed

- Reusable utilities
- Clean code structure
- Separation of concerns
- Centralized configuration
- Generic helper methods
- Minimal hardcoding
- Independent test cases

---

## Future Enhancements

- Docker integration
- Allure reporting
- API schema validation
- Database validation
- Contract testing
- OAuth2 authentication support
- Kafka event validation

---

## Author

Developed for scalable and maintainable API automation testing using Java ecosystem tools.

---

## Challenges Faced

During framework development, the following challenges were encountered:

### 1. Retry Handling for Flaky APIs
Some APIs returned intermittent failures or unstable responses.  
A reusable retry mechanism was implemented using Java functional interfaces to automatically retry failed API calls.

### 2. Generic Request Handling
Creating reusable API helper methods while supporting:
- Headers
- Query parameters
- Path parameters
- Authentication
- Multiple content types

required careful abstraction to keep the framework flexible and maintainable.

### 3. Parallel Execution Stability
Ensuring independent test execution during parallel runs required:
- Proper test isolation
- Avoiding shared mutable data
- Dynamic test data generation

### 4. Logging and Debugging
Capturing request and response details only for failed validations was implemented to improve debugging without cluttering logs.

### 5. Serialization and Deserialization
Managing nested request/response payloads required proper POJO design using Jackson annotations and Lombok.