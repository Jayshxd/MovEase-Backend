# MovEase-Backend: A Comprehensive Movie Ticket Booking System

MovEase-Backend is a robust and feature-rich backend system for a modern movie ticket booking application, similar to BookMyShow. It provides a complete set of RESTful APIs to manage movies, theatres, shows, bookings, and user authentication. The project is built with Spring Boot and follows best practices for building scalable and maintainable web applications.

## ✨ Features

### User Features
- **User Authentication:** Secure user registration and login with JWT (JSON Web Tokens).
- **Browse Movies:** View a list of all available movies with details like cast, director, genre, and release date.
- **Search and Filter:** Search for movies by name, genre, or language.
- **View Theatres:** See a list of all theatres in a specific city.
- **Check Showtimes:** Find showtimes for a specific movie in a given theatre.
- **Seat Selection:** View the seat layout for a show and select seats.
- **Book Tickets:** Book tickets for a show and receive a booking confirmation.
- **Payment Integration:** Integrated with Razorpay for seamless and secure payments.
- **View Booking History:** Users can view their past bookings.

### Admin Features
- **CRUD Operations for Movies:** Add, update, and remove movies.
- **CRUD Operations for Theatres:** Add, update, and remove theatres.
- **CRUD Operations for Screens:** Manage screens within a theatre.
- **CRUD Operations for Shows:** Schedule and manage movie showtimes.
- **Manage Bookings:** View all bookings and their statuses.

## 🛠️ Technologies & Tools

- **Framework:** Spring Boot 3.3.1
- **Language:** Java 17
- **Database:** MySQL
- **Data Access:** Spring Data JPA (Hibernate)
- **Security:** Spring Security, JWT (JSON Web Tokens)
- **API Documentation:** SpringDoc OpenAPI (Swagger UI)
- **Payment Gateway:** Razorpay
- **Build Tool:** Maven
- **Utilities:** Lombok, Spring Boot DevTools

## 🏗️ Architecture

The application follows a classic N-tier architecture, separating concerns into different layers:

- **Controller Layer:** Handles incoming HTTP requests, validates input, and delegates to the service layer.
- **Service Layer:** Contains the core business logic of the application.
- **Repository Layer:** Responsible for data access and communication with the database using Spring Data JPA.
- **Entity Layer:** Defines the data model and database schema.
- **Security Layer:** Manages authentication and authorization using Spring Security and JWT.

## 🗄️ Database Schema

The database schema is designed to be normalized and efficient. The main entities and their relationships are as follows:

- `User`: Stores user information and has a many-to-many relationship with `Role`.
- `Role`: Stores user roles (e.g., `ROLE_USER`, `ROLE_ADMIN`).
- `Movie`: Contains movie details and has a one-to-many relationship with `Show`.
- `Theatre`: Stores theatre information and has a one-to-many relationship with `Screen`.
- `Screen`: Represents a screen in a theatre and has one-to-many relationships with `Seat` and `Show`.
- `Seat`: Represents a physical seat in a screen.
- `Show`: Represents a specific show of a movie in a screen and has one-to-many relationships with `Booking` and `ShowSeat`.
- `ShowSeat`: Represents the state of a seat for a particular show (e.g., available, booked).
- `Booking`: Stores booking details and has relationships with `User`, `Show`, and `ShowSeat`.

## 🚀 API Endpoints

The application provides a rich set of RESTful APIs. You can explore and test the APIs using the Swagger UI at `http://localhost:8080/swagger-ui.html` after running the application.

Here's a summary of the available endpoints:

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/users/register` | Register a new user. |
| `POST` | `/users/login` | Authenticate a user and get a JWT token. |
| `GET` | `/movies` | Get a list of all movies. |
| `GET` | `/movies/{id}` | Get details of a specific movie. |
| `POST` | `/movies` | Add a new movie (Admin only). |
| `GET` | `/theatres` | Get a list of all theatres. |
| `POST` | `/theatres` | Add a new theatre (Admin only). |
| `GET` | `/shows/movie/{movieId}` | Get all shows for a movie. |
| `POST` | `/shows` | Add a new show (Admin only). |
| `GET` | `/shows/{showId}/seats` | Get seat availability for a show. |
| `POST` | `/bookings` | Create a new booking. |
| `GET` | `/bookings/user/{userId}` | Get all bookings for a user. |
| `POST` | `/bookings/payment` | Handle payment confirmation from Razorpay. |

... and many more for managing screens, seats, and other resources.

## ⚙️ Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

- Java 17
- Maven
- MySQL

### Installation

1. **Clone the repository**
   ```sh
   git clone https://github.com/Jayshxd/MovEase-Backend.git
   ```
2. **Navigate to the project directory**
   ```sh
   cd MovEase-Backend
   ```
3. **Create a MySQL database**
   - Create a new database named `bookmyshow_db`.
4. **Configure the application**
   - Open `src/main/resources/application.properties` and update the database credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/bookmyshow_db
     spring.datasource.username=your_mysql_username
     spring.datasource.password=your_mysql_password
     ```
   - You'll also need to configure your Razorpay API keys and JWT secret:
     ```properties
     razorpay.key_id=YOUR_RAZORPAY_KEY_ID
     razorpay.key_secret=YOUR_RAZORPAY_KEY_SECRET
     jwt.secret=your_jwt_secret_key
     ```
5. **Run the application**
   ```sh
   mvn spring-boot:run
   ```
   The application will start on `http://localhost:8080`.

## 👤 Author

**Jayesh**

- GitHub: [@Jayshxd](https://github.com/Jayshxd)

---
