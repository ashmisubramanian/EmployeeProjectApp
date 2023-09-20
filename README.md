# Employee Management System 

## Introduction

The Employee Management System is a specialized backend application tailored to efficiently handle employee and project management tasks while also accommodating manager-related data. It is composed of two distinct Spring Boot applications: "springboot-crud-web-app," which primarily deals with employee and project functionalities, and "managerControl," dedicated to storing manager details independently. This comprehensive system enables seamless CRUD (Create, Read, Update, Delete) operations on employee, project, and manager records through a well-structured REST API. Furthermore, it facilitates secure data exchange between these two discrete Spring applications. Additionally, the system offers user registration and login functionalities within the "springboot-crud-web-app" component, enhancing its versatility and utility.

## Technologies Used

- **Backend**: Spring Boot
- **Database**: PostgreSQL
- **API Testing and Documentation Tools**: Postman

## Functionalities

### 1. CRUD operations using RESTful APIs

The system provides a robust RESTful API for managing employee, project, and manager records, offering a diverse array of functionalities:

- **Create**: Seamlessly introduce new employee, project, or manager records into their respective systems.
- **Read**: Retrieve records comprehensively or based on specific criteria, including the utilization of JPQL query language.
- **Update**: Effortlessly enhance and update existing records as needed.
- **Delete**: Swiftly remove unwanted records from the database.

### 2. Table Relationships

- Employees and projects share a dynamic many-to-many relationship, allowing projects to be seamlessly added as attributes during employee setup. This intuitive approach simplifies the process of assigning employees to specific projects. Additionally, a dedicated join table manages this relationship while ensuring project manager validation.

### 3. Leveraging RestTemplate and WebClient

- This project seamlessly harnesses the power of RestTemplate and WebClient to facilitate data exchange between two independent applications: springboot-crud-web-app and managerControl. When you create a new project in springboot-crud-web-app, it automatically stores manager details in managerControl. Likewise, managerControl allows you to access all projects managed by a specific manager within springboot-crud-web-app.


## Configuration

Make sure to set up the system's essential configurations, including:

- **Database Setup**: Configure the PostgreSQL database connection by providing the database URL, username, and password in the application's configuration files.


## Learnings

- **RESTful API Development:** Gained hands-on experience in developing a RESTful API for efficient data management.
- **Database Relationship Handling:** Gained experience in handling various database relationships, to model complex data structures effectively.
- **Database Configuration:** Learned how to configure a PostgreSQL database, including setting up the database URL, username, and password.
- **Integration Techniques:** Understood and implemented the integration techniques, such as RestTemplate and WebClient, to enable data transfer between distinct applications.
- **JPQL Query Language:** Gained familiarity with JPQL, a versatile database query language in Java, enabling precise data retrieval and modification for various system requirements.

## License

This project is licensed under the [MIT License](LICENSE.md).


