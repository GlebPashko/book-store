# Online Book Store

[![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=java&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Hibernate](https://img.shields.io/badge/Hibernate-4D5B41?style=flat-square&logo=hibernate&logoColor=white)](https://hibernate.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)](https://www.docker.com/)
[![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=swagger&logoColor=white)](https://swagger.io/)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Liquibase](https://img.shields.io/badge/Liquibase-3D5B3A?style=flat-square&logo=liquibase&logoColor=white)](https://www.liquibase.org/)
[![MapStruct](https://img.shields.io/badge/MapStruct-80A0B1?style=flat-square&logo=mapstruct&logoColor=white)](https://mapstruct.org/)

## Table of Contents
- [About](#about)
- [Technologies Used](#technologies-used)
- [Functionalities](#functionalities)
- [Project Setup and Launch](#project-setup-and-launch)
- [Challenges and Solutions](#challenges-and-solutions)
- [Quick Demo Video](#quick-demo-video)

## About
The online-book-store is a web application developed in Java using the Spring Boot framework. It provides a complete platform for handling books, categories, user registrations, shopping carts, and orders in a bookstore. The project was created to offer an efficient and scalable solution for simplifying bookstore management.

## Technologies Used
* **Spring Boot**: Is a powerful framework for building Java Applications.
* **Spring Security:** Is a powerful and highly customizable authentication and access-control 
  framework.
* **Spring Data JPA:** Simplifies the implementation of JPA-based (Java Persistence API) repositories.
* **Hibernate:** ORM tool for Java applications.
* **MySQL:** A relational database management system.
* **Liquibase:** Database schema migration tool.
* **Docker:** Docker helps developers build, share, run, and verify applications in containers.
* **Lombok:** A library to reduce boilerplate code in Java.
* **MapStruct:** Implements mapping between DTOs and entity models.
* **Swagger:** Tool for API documentation and testing.

## Functionalities
### User Management
* **User registration.**
  ``` 
  POST: /auth/registration
  ``` 
  Example of request body to register user:
  ```json
  {
    "email": "bob@mail.com",
    "password": "BobPassword1234",
    "repeatPassword": "BobPassword1234",
    "firstName": "Bob",
    "lastName": "Aye",
    "shippingAddress": "Kyiv"
  }
  ```
  
* **Secure user login with JWT-based authentication.**
  ``` 
  POST: /auth/login
  ``` 

  Example of request body to do log-in:
  ```json
  {
  "email": "bob@mail.com",
  "password": "BobPassword1234"
  }
  ```

  Example of response body after successful log-in. Use generated JWT token in the Authorization 
  header in your requests.
  ```json
  {
  "token": "eyJhbGci .... .... eoWbArcG7o-CNQO2Jo"
  }
  ```

### Book Management
* **Create, retrieve, update, and delete books.**
* **Search for books based on various parameters.**
* **Associate books with multiple categories.**
<br/><br/>
**Available endpoints for Book Management**

  with USER role
  ``` 
  GET: /books
  
  GET: /books/{id} 
  
  GET: /books/search
  ```

  with ADMIN role
  ``` 
  POST: /books/
  
  DELETE: /books/{id}
  
  PUT: /books/{id}
  ``` 

  Example of request body to **create new book**:
  ```json
  {
    "title": "Book title",
    "author": "Book author",
    "price": "100", 
    "description": "Description for book",
    "coverImage": "Book image",
    "isbn": "1234567890",
    "categoryIds": [1, 2]
  }
  ```
  If you want to add category to book, you should crate category first, or update book later. Field categoryIds is an optional field.

  To update Book you should use same request body as for creation of a new book.

### Category Management
* **Create, retrieve, update, and delete book categories.**
  <br/><br/>
  **Available endpoints for Category Management**

  with USER role
  ``` 
  GET: /categories
  
  GET: /categories/{id}
  
  GET: /categories/{id}/books
  ``` 
  
  with ADMIN role
  ``` 
  POST: /categories
  
  PUT: /categories/{id}
  
  DELETE: /categories/{id}
  ``` 

  Example of request body to **create new category**:
  ```json
  {
    "name": "Category name",
    "description": "Category description"
  }
  ```

### Shopping Cart and Order Management

* Add books to the shopping cart.
* View and manage shopping cart items.
* Place orders, update order status, and retrieve order details.
  <br/><br/>
**Available endpoints for Shopping Cart Management**

  with USER role
  ```
  POST: /cart
  
  GET: /cart
  
  PUT: /cart/cart-items/{cartItemId}
  
  DELETE: /cart/cart-items/{cartItemId}
  ```
  Example of request body to **add items in cart**:
  ```json
  {
    "bookId": 1,
    "quantity": 1
  }
  ```
  
  Example of request body to **update book qty in cart**:
  ```json
  {
  "quantity": 1
  }
  ```

  ****Available endpoints for Order Management****

  with USER role
  ``` 
  POST: /orders
  
  GET: /orders
  
  GET: /orders/{orderId}/items
  
  GET: /orders/{orderId}/items/{itemId}
  ```
  Example of request body to **post order**:
  ```json
  {
  "shippingAddress": "1234 Main St, City"
  }
  ```
  with ADMIN role
  ```
  PUT: /orders/{id}
  ``` 
  Example of request body to **update order status**:
  ```json
  {
  "status": "CANCELED"
  }
  ```

## Project Setup and Launch

To build and start the project on your PC, follow these steps:
### 1. Clone the Project
- Go to the project repository and press the *Code* button to copy the HTTPS link.
- In your IDE (e.g., IntelliJ IDEA), select *Get from VCS*, paste the link, and click *Clone*.
### 2. Set Up Environment Variables
- Create a .env file in the project root, and populate it with the necessary variables from file env.sample.
- You can change the port settings. By default, requests from the .env.sample file are sent to port 8088.
### 3. Start Docker
- Make sure Docker is running on your machine. You can verify Docker's status with the command:
```
docker --version
```
### 4. Compile the Project
- In the IDE terminal, run:
```
mvn package
```
### 5. Run the Application in Docker
- To start the application in Docker, run:
```
docker-compose up
```
### 6. Import the API Collection (Optional)
- If an Apidog collection is available, you can import it into Apidog to simplify API endpoint testing. Look for the collection file in the project repository (e.g., book.apidog).
- Alternatively, you can use the OpenAPI specification (book.openapi) to import the API into other API tools that support OpenAPI, such as Postman or Swagger.

### Prerequisites
Ensure that the following technologies are installed:
- Java: Version 17.0 (for compiling)
- Maven: Version 3.9 (for dependency management)
- Docker: Version 27.1.1 (for containerization)
## Challenges and Solutions

- **N + 1 problem**  
  I resolved this by using **Join Fetching** to load related data in a single query, avoiding unnecessary database calls and improving performance.

- **Database Design**  
  Initially, the database schema lacked normalization and had some redundant data. I reworked the schema to improve entity relationships, ensuring proper indexing, normalization, and data integrity, which resulted in better performance.

- **Lazy Initialization Exception**  
  To avoid the Lazy Initialization Exception, I used **Join Fetching** in queries instead of relying on lazy loading, which helped load all necessary data in the same transaction scope and eliminated the exception.

- **Containerization with Docker**  
  Initially, setting up **Docker** for the development and production environments was tricky due to differences in local and containerized setups. I overcame this by carefully configuring Dockerfiles, handling dependencies, and ensuring proper networking between the application and database containers.

- **Mapping Issues with MapStruct**  
  When using **MapStruct** to map between DTOs and entity models, I faced issues with nested objects not being mapped correctly. I resolved this by adding custom mapping methods for complex objects and ensuring correct configuration of mappers.

## Quick Demo Video

[Дивитись відео на Loom](https://www.loom.com/share/f18d781ae99e4bbb89c954f882ecfcb8?sid=6b8958f9-6310-41ab-aed4-2497452df7a5)



