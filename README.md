# Contact Registry API

## Overview
This project provides a SOAP and RESTful API for managing a contact registry. Users can query contacts based on phone number, masked details, or organization name. The API is designed to support integration with external applications.

## Setup Instructions

### Prerequisites
- Java 11+
- Apache Tomcat 
- MySQL Database
- Maven (for dependency management)
- NetBeans (recommended for development)

### Steps to Run the Application
1. Clone the repository:
   ```sh
   git clone https://github.com/nyadero/contactsregistry.git
   cd contactsregistry
   ```
2. Configure the database (see script below).
3. Build the project using Maven:
   ```sh
   mvn clean package
   ```
4. Deploy the application on Tomcat.
5. Start the SOAP and REST services:
   - SOAP: `http://localhost:8040/contactsSoapService?wsdl`
   - REST: `http://localhost:8080/api/contacts`

## Database Schema & Script

Run the following SQL script to set up the database:

```sql
CREATE DATABASE contact_registry;
USE contact_registry;

CREATE TABLE contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    masked_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    id_number VARCHAR(255) NOT NULL,
    gender VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL UNIQUE,
    masked_phone_number VARCHAR(20) NOT NULL UNIQUE,
    hashed_number VARCHAR(80) NOT NULL UNIQUE,
    organization VARCHAR(255) NOT NULL
);

INSERT INTO contacts (full_name, email, id_number, gender, phone_number, organization) 
VALUES
('John Doe', 'johndoe@example.com', '12345678', 'Male', '0700123456', 'TechCorp'),
('Jane Smith', 'janesmith@example.com', '87654321', 'Female', '0712345678', 'HealthOrg'),
('Alice Johnson', 'alicej@example.com', '23456789', 'Female', '0723456789', 'EduCare'),
('Bob Marley', 'bobmarley@example.com', '98765432', 'Male', '0734567890', 'MusicInc'),
('Charlie Brown', 'charlieb@example.com', '34567890', 'Male', '0745678901', 'FoodiesLtd');

```

## API Endpoints

### SOAP API
- **WSDL URL:** `http://localhost:8040/contactsSoapService?wsdl`
- **Methods:**
  - `getContactsByOrganization(organization: String)`: Returns a list of contacts for the given organization.

### REST API  
- **Base URL:** `http://localhost:8080/api/contacts`  
- **Endpoints:**  
  - `GET /` – Get all contacts.  
  - `GET /{id}` – Get a contact by ID.  
  - `GET /organization/{organization}` – Get all contacts in an organization.  
  - `GET /search?phone={phone_hash}` – Find contact by phone number hash.  
  - `GET /search?name={masked_name}&phone={masked_phone}` – Find contact by masked details.  
  - `POST /` – Add a new contact.  
  - `PUT /{id}` – Update an existing contact.  
  - `DELETE /{id}` – Delete a contact.  


## Consuming API in the JSP Web App

- Use JavaScript to make AJAX requests to the REST API and display data in a table.
- Ensure the correct `Content-Type` headers (`application/json`).

---
**Author:** BowerzLabs

