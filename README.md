## Apartment Rental Management System
A web-based platform developed as part of the Distributed Systems course at Harokopio University of Athens. It enables users to browse available apartments, submit and approve new listings, manage rental applications, and enforce role‑based access control (User, Owner, Tenant).

---

## Features

- **Browse Apartments**: View all available apartments for rent.  
- **Listing Submission**: First‑time listers become `ROLE_OWNER` and submit apartment details.  
- **Rental Applications**: First‑time renters become `ROLE_TENANT` and enter tenant details.  
- **Role‑Based Access Control**: Only authorized roles can perform specific actions (e.g., only owners can delete their own listings).

---

## Technologies Used

- **Java 21+**  
- **Spring** — opinionated framework for rapid, production‑ready apps
- **Thymeleaf** — server‑side Java template engine for HTML views
- **Spring Data JPA** — ORM for database interactions  
- **Spring Security** — authentication and authorization  
- **PostgreSQL** — relational database for storing data  
- **Maven** — build and dependency management

---

## Prerequisites

- Java 21 or higher  
- Maven 3.6+  
- A relational database (e.g., MySQL, PostgreSQL)
- Internet access

---

## Installation

1. **Clone the repository**  
   ```bash
   git clone https://github.com/AlexZarkalis/real-estate.git

2. **Build the project**  
   ```bash
   mvn clean package

3. **Configure (see Configuration below)**  

2. **Run**  
   ```bash
   mvn spring-boot:run

---

## Configuration

Add your database to  _src/main/resources/application.properties_

**Application Name / Server Port:**

    spring.application.name=<YOUR_APP_NAME>
    server.port=8080

**Database Connection:**

    spring.datasource.url=jdbc:mysql://<HOST>:<PORT>/<DB_NAME>
    spring.datasource.username=<DB_USER>
    spring.datasource.password=<DB_PASS>

## Usage

**Tenant Workflow**

    1.Login

    2.View available apartments

    3.Apply to rent (first‑time tenants fill personal details)

**Owner Workflow**

    1.Login

    2.Create new apartment listing (first‑time owners fill personal details)

    3.Await admin approval

    4.Manage listings (edit/delete own apartments)
