# E-Commerce Store API

Backend REST API for an e-commerce system built with Spring Boot.
The project covers the core features of an online store including authentication, product management, cart handling, orders, and Stripe payments.

---

## Overview

This project was built to practice designing a real-world backend system with:

* Secure authentication using JWT
* Role-based authorization (User / Admin)
* Cart and order lifecycle management
* Payment processing with Stripe
* Database versioning using Flyway
* Dockerized setup for easier deployment

---

## Tech Stack

* Java 21
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* MySQL
* Flyway
* Stripe API
* Docker
* Maven

---

## Main Modules

* `auth` – authentication & JWT handling
* `users` – user management
* `products` – products & categories
* `carts` – cart operations
* `orders` – order processing
* `payments` – Stripe integration
* `admin` – admin-only operations
* `common` – shared configuration & utilities

---

## Running the Application

### Using Maven

```bash
mvn clean install
mvn spring-boot:run
```

### Using Docker

```bash
docker-compose up --build
```

---

## Environment Variables

Create a `.env` file or configure the following:

```
JWT_SECRET=
STRIPE_SECRET_KEY=
STRIPE_WEBHOOK_SECRET_KEY=
SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
SETUP_TOKEN=
MYSQL_DATABASE=
MYSQL_ROOT_PASSWORD=
MYSQL_USER=
MYSQL_PASSWORD=
```

---

## API Documentation

Swagger UI is available after running the app:

```
http://localhost:8080/swagger-ui.html
```

---

## What This Project Demonstrates

* Designing layered architecture (Controller → Service → Repository)
* Implementing stateless authentication with JWT
* Handling business logic for cart and order workflows
* Integrating third-party payment gateway (Stripe)
* Managing database schema changes with Flyway
* Writing maintainable and structured backend code

---

## Author

Yahia ElPrince
[https://github.com/yahiaprince](https://github.com/yahiaprince)
LinkedIn: (www.linkedin.com/in/yahia-elprince)
