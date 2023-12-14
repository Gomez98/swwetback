# Spring Boot Backend Project

This is a backend project developed using Java and Spring Boot. It serves as the backend for a web application, providing RESTful APIs and interacting with a MySQL database.

## Technologies Used

- **Java:** The primary programming language used for backend development.
- **Spring Boot:** A powerful and flexible framework for building Java-based enterprise applications.
- **Spring Data JPA:** Simplifies the implementation of data access layers.
- **Spring Web:** Provides basic web support.
- **Lombok:** Reduces boilerplate code by automatically generating getter, setter, and other methods.
- **MySQL Connector/J:** Enables the application to connect to a MySQL database.
- **Springdoc OpenAPI:** Generates OpenAPI documentation for the APIs.

## Project Structure

The project follows the standard Maven project structure:

- **src/main/java:** Contains the Java source code.
- **src/main/resources:** Contains configuration files, application properties, and static resources.
- **src/test:** Contains test classes.

## Getting Started

To run the project locally, follow these steps:

1. Clone this repository: `git clone https://github.com/Gomez98/swwetback`
2. Build the project: `mvn clean install`
3. Run the application: `mvn spring-boot:run`

The application will be accessible at [http://localhost:8080](http://localhost:8080).

## Configuration

- **Database Configuration:** The application is configured to use MySQL. Update the `application.properties` file with your database connection details.

## Notes

Don't forget to review the application.properties file for more information on project configuration

## Contribution

Contributions are welcome! If you find bugs or improvements, please open an issue or send a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
