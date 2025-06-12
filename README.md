# Pet Vaccination Management System

A robust Spring Boot application designed to manage pet details and their vaccination records. It provides a clean, RESTful API for seamless interaction, built with a strong emphasis on modern software design principles and patterns.

##  Features

- **Pet Management (CRUD)**:
    - Create new pet records, including their vaccination history.
    - Retrieve details of all registered pets.
    - Fetch specific pet details by ID.
    - Update existing pet information and vaccination records.
    - Delete pet records.
- **Vaccination Search**:
    - Search for pets based on a specific vaccination name.
- **Standardized API Responses**: All API operations return a consistent `ResponseDTO` structure, simplifying client-side consumption for both success and error scenarios.
- **Centralized Error Handling**: A global exception handler ensures uniform and consistent error responses across the API.
- **Layered Architecture**: Clear separation of concerns (Controller, Service, Repository, DTOs, Entities, Mapper).
- **Aspect-Oriented Programming (AOP)**: Utilizes Spring AOP for centralized logging of service method execution, keeping business logic clean and focused.
- **Type-Safe Data Mapping**: Dedicated `Mapper` class handles conversions between DTOs and entities, ensuring data integrity and readability.

##  Technologies Used

- **Spring Boot**: Framework for building production-ready, stand-alone Spring applications.
- **Spring Data JPA**: Simplifies data access and persistence with JPA.
- **Hibernate**: The JPA implementation for Object-Relational Mapping (ORM).
- **H2 Database (In-memory)**: Used for development and testing, easily configurable for external databases like MySQL or PostgreSQL.
- **Lombok**: Reduces boilerplate code (getters, setters, constructors, builders).
- **Maven**: Dependency management and build automation.
- **Jakarta Validation**: For declarative validation of request bodies.

## ️ Getting Started

The application will typically start on `http://localhost:8080` by default.

## 💡 Design Principles & Patterns

This project is structured adhering to several key software design principles and patterns:

- **Single Responsibility Principle (SRP)**: Each class (e.g., PetDetailsController, PetServiceImplementation, PetDetailsRepository, Mapper, DTOs) has a single, well-defined reason to change.

- **Separation of Concerns (SoC)**: The codebase is clearly divided into distinct layers:
    - **Presentation Layer**: PetDetailsController handles HTTP requests and responses.
    - **Business/Service Layer**: PetServiceImplementation contains the core application logic.
    - **Data Access Layer**: PetDetailsRepository manages database interactions.
    - **Data Transfer Objects (DTOs)**: Act as data carriers between layers and define API contracts.

- **Dependency Inversion Principle (DIP)**: High-level modules (PetDetailsController) depend on abstractions (PetDetailsServices interface) rather than concretions (PetServiceImplementation), promoting loose coupling and testability.

- **Open/Closed Principle (OCP)**: The system is designed to be open for extension (e.g., new service implementations, new logging aspects) but closed for modification of existing, tested code.

- **Data Transfer Object (DTO) Pattern**: PetRequestDTO and PetDTO are used to map between the API and the internal entity model, preventing direct exposure of entities.

- **Repository Pattern**: PetDetailsRepository abstracts the data persistence layer, providing a collection-like interface for data operations.

- **Service Layer Pattern**: PetServiceImplementation encapsulates business logic, orchestrating data access and applying domain rules.

- **Builder Pattern**: Extensively used via Lombok's @Builder annotation for creating instances of entities and DTOs in a readable and flexible manner.

- **Standardized API Response Structure**: The consistent use of `ResponseEntity<ResponseDTO>` across all controller methods provides a predictable and easy-to-consume API.

##  API Endpoints

The API is accessible under the `/api/pets` base path. All responses, whether successful or erroneous, will adhere to the `ResponseDTO` format.

### Response Structure

```json
{
  "message": "A descriptive message about the operation.",
  "data": {
    /* The actual data payload (e.g., a PetDTO object or list of PetDTOs), or null for operations like deletion. */
  }
}
```

### Pet Management Endpoints

#### POST `/api/pets`
- **Description**: Creates a new pet record.
- **Request Body (PetRequestDTO)**:
  ```json
  {
    "name": "Buddy",
    "species": "DOG",
    "breed": "Golden Retriever",
    "ownerName": "Alice Smith",
    "ownerContact": "1234567890",
    "vaccines": [
      {
        "name": "Rabies",
        "dateGiven": "2023-01-15"
      },
      {
        "name": "Distemper",
        "dateGiven": "2023-03-01"
      }
    ]
  }
  ```
- **Success Response**:
  ```json
  {
    "message": "Successfully registered new Pet",
    "data": {
      "id": 1,
      "name": "Buddy",
      "species": "DOG",
      "breed": "Golden Retriever",
      "ownerName": "Alice Smith",
      "ownerContact": "1234567890",
      "vaccines": [...]
    }
  }
  ```
- **Error Response**:
  ```json
  {
    "message": "Validation error: Pet name cannot be blank",
    "data": null
  }
  ```

#### GET `/api/pets`
- **Description**: Retrieves a list of all registered pets.
- **Request Body**: None
- **Success Response**:
  ```json
  {
    "message": "Successfully retrieved all 2 pets details",
    "data": [
      {
        "id": 1,
        "name": "Buddy",
        "species": "DOG",
        "breed": "Golden Retriever",
        "ownerName": "Alice Smith",
        "ownerContact": "1234567890",
        "vaccines": [...]
      },
      {
        "id": 2,
        "name": "Whiskers",
        "species": "CAT",
        "breed": "Persian",
        "ownerName": "Bob Johnson",
        "ownerContact": "0987654321",
        "vaccines": [...]
      }
    ]
  }
  ```
- **Note**: Typically returns HTTP 200 OK with an empty list if no pets found.

#### GET `/api/pets/{id}`
- **Description**: Retrieves details for a specific pet by its ID.
- **Path Parameter**: `{id}` - Pet ID
- **Request Body**: None
- **Success Response**:
  ```json
  {
    "message": "Fetched Pet details with pet Id: 1",
    "data": {
      "id": 1,
      "name": "Buddy",
      "species": "DOG",
      "breed": "Golden Retriever",
      "ownerName": "Alice Smith",
      "ownerContact": "1234567890",
      "vaccines": [...]
    }
  }
  ```
- **Error Response**:
  ```json
  {
    "message": "Pet with ID: 99 was not found.",
    "data": null
  }
  ```

#### PUT `/api/pets/{id}`
- **Description**: Updates an existing pet's details by ID.
- **Path Parameter**: `{id}` - Pet ID
- **Request Body (PetRequestDTO)**:
  ```json
  {
    "name": "Buddy Updated",
    "species": "DOG",
    "breed": "Golden Retriever",
    "ownerName": "Alice Smith",
    "ownerContact": "0987654321",
    "vaccines": [
      {
        "name": "Rabies",
        "dateGiven": "2023-01-15"
      },
      {
        "name": "Parvo",
        "dateGiven": "2024-05-20"
      }
    ]
  }
  ```
- **Success Response**:
  ```json
  {
    "message": "Pet updated successfully",
    "data": {
      "id": 1,
      "name": "Buddy Updated",
      "species": "DOG",
      "breed": "Golden Retriever",
      "ownerName": "Alice Smith",
      "ownerContact": "0987654321",
      "vaccines": [...]
    }
  }
  ```
- **Error Response**:
  ```json
  {
    "message": "Pet with ID: 99 was not found.",
    "data": null
  }
  ```

#### DELETE `/api/pets/{id}`
- **Description**: Deletes a pet record by ID.
- **Path Parameter**: `{id}` - Pet ID
- **Request Body**: None
- **Success Response**: HTTP 204 No Content
  ```json
  {
    "message": "Pet deleted successfully",
    "data": null
  }
  ```
- **Error Response**:
  ```json
  {
    "message": "Pet with ID: 99 was not found.",
    "data": null
  }
  ```

#### GET `/api/pets/vaccinated/{name}`
- **Description**: Retrieves pets that have received a specific vaccine.
- **Path Parameter**: `{name}` - Vaccine name
- **Request Body**: None
- **Success Response**:
  ```json
  {
    "message": "Fetched all pet details with vaccination: Rabies",
    "data": [
      {
        "id": 1,
        "name": "Buddy",
        "species": "DOG",
        "breed": "Golden Retriever",
        "ownerName": "Alice Smith",
        "ownerContact": "1234567890",
        "vaccines": [...]
      }
    ]
  }
  ```
- **Note**: Typically returns HTTP 200 OK with an empty list if no pets found with the specified vaccination.

## 🏗️ Project Structure

```
src/main/java/
├── controller/          # REST Controllers
├── service/            # Business Logic Services
├── repository/         # Data Access Layer
├── entity/            # JPA Entities
├── dto/               # Data Transfer Objects
├── mapper/            # Entity-DTO Mapping
└── exception/         # Custom Exceptions

src/main/resources/
├── application.properties
```

