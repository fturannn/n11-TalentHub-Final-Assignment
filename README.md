# n11-TalentHub-Final-Assignment
n11 TalentHub Spring Boot Bootcamp Final Project

### Deployment Steps:

1- Ensure that Docker is installed on your system.

2- Clone the project repository from GitHub.

3- Navigate to the project directory in your terminal.

5- Run docker-compose up command to start the Docker containers.

6- Once the containers are up and running, access the application using the specified endpoints.

## Features:

### User Management:

Users can be registered by providing details such as name, surname, latitude, and longitude.

User information can be retrieved, updated and deleted.

### Review Management:

Users can review restaurants by providing a rating between 1 and 5.

Reviews can be managed and deleted, including text and rating updates.

### Restaurant Management:

Restaurants can be retrieved, registered, updated, and deleted along with necessary details (including latitude and longitude).

All restaurants can be listed.

### Recommendation Service:

Users can receive restaurant recommendations based on their location and restaurant ratings.

Recommended restaurants are selected ensuring they are not farther than 10 kilometers.

Closer restaurants are evaluated based on distance weight (%30), while restaurant rating weight is considered as %70.

### Communication with Restaurant Service:

Feign client is utilized to establish communication with the Restaurant service.

The Feign client allows seamless integration with the Restaurant service to retrieve restaurant information and recommendations.

### Logging and Error Handling:

Errors and information logs generated within the services are captured.

Captured logs are persisted into a PostgreSQL database using RabbitMQ message broker.

This allows for effective monitoring, troubleshooting, and analysis of system behavior.

### Dockerized Deployment:

The application can be deployed easily using Docker Compose.

Docker Compose orchestrates the deployment of all necessary services, ensuring seamless setup and execution.

### Technologies:

- A robust backend infrastructure is built using Spring Boot and Hibernate.

- Apache Solr is utilized for storing and querying restaurant data.

- PostgreSQL is utilized for storing and querying user, user review & log datas.

- Unit and Integration tests are written to ensure code quality and reliability.

- Swagger OpenAPI is used for API documentation and exploration.

- Logging mechanism and Exception handling are integrated to enhance system reliability.

- RabbitMQ message broker is used for logging and error handling, with logs persisted into a PostgreSQL database.

- Docker Compose is employed for easy deployment and management of the application.

### The project encompasses the following endpoints:

#### User Service

| HTTP Method | Endpoint                                   | Description                              |
|-------------|--------------------------------------------|------------------------------------------|
| GET         | /api/v1/users                              | Retrieves all users                      |  
| GET         | /api/v1/users/{id}                         | Retrieves user details based on the provided ID      |
| GET         | /api/v1/users/recommended-restaurants/{id} | Retrieves recommended restaurants for the user based on the provided ID       |
| POST        | /api/v1/users                              | Saves a new user based on the provided request                      |
| PUT         | /api/v1/users/{debugUserId}                | Updates an existing user based on the provided request and ID |
| DELETE      | /api/v1/users/{id}                         | Deletes the user based on the provided ID        |
| PATCH       | /api/v1/users/{id}/activate                | Activates the user based on the provided ID|
| PATCH       | /api/v1/users/{id}/deactivate              | Deactivates the user based on the provided ID    |

#### User Review Service

| HTTP Method | Endpoint                                          | Description                              |
|-------------|---------------------------------------------------|------------------------------------------|
| GET         | /api/v1/reviews                                   | Get a list of all user reviews                      |  
| GET         | /api/v1/reviews/{id}                              | Get a user review by its ID      |
| GET         | /api/v1/reviews/with-user-name                    | Get a list of user reviews by user name       |
| GET         | /api/v1/reviews/with-user-id/{userId}             | Get a list of user reviews by user ID |
| GET         | /api/v1/reviews/with-restaurant-id/{restaurantId} | Get a list of user reviews by restaurant ID  |
| POST        | /api/v1/reviews                                   | Create a new user review                      |
| PUT         | /api/v1/reviews/{debugReviewId}                   | Update an existing user review |
| DELETE      | /api/v1/reviews/{id}                              | Delete a user review by its ID        |

#### Restaurant Service

| HTTP Method | Endpoint                                         | Description                              |
|-------------|--------------------------------------------------|------------------------------------------|
| GET         | /api/v1/restaurants                                 | Retrieves all active restaurants                      |  
| GET         | /api/v1/restaurants/{id}                            | Returns the restaurant details based on the provided ID      |
| GET         | /api/v1/restaurants/restaurant-name-like/{name}    | Retrieve restaurants whose names contain the provided substring       |
| POST        | /api/v1/restaurants                                 | Saves a new restaurant based on the provided request    |
| PUT         | /api/v1/restaurants/{debugRestaurantId}      | Updates an existing restaurant based on the provided request and ID |
| PUT         | /api/v1/restaurants/{id}/score      | Updates the score of the restaurant based on the provided ID and new score                                                                    |
| DELETE      | /api/v1/restaurants/{id}                             | Deletes the restaurant based on the provided ID        |