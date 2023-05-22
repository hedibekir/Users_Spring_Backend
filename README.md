# Spring Boot 3.0 Customers Accounts Management Backend

## Features
* Admin login with JWT authentication
* Customers Accounts Management
* Password encryption using BCrypt
* Role-based authorization with Spring Security
* Customized access denied handling
* Refresh token

## Technologies
* Kotlin
* Spring Boot 3.0
* Spring Security
* JOOQ
* FlyWay
* JSON Web Tokens (JWT)
* BCrypt
* Gradle

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Docker & Docker Compose


To build and run the project, follow these steps:

* Clone the repository: `git clone https://github.com/hedibekir/Users_Spring_Backend.git`
* Navigate to the project directory: cd Users_Spring_Backend-master
* Launch PostgresSql database container: docker-compose up
* Build & Run the project
* Default Admin Account Credentials: Email: admin@test.com, Password: admin  -> The application will be available at http://localhost:8080.