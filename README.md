# FlyingFeimVideoClub
Video club manager, an educational project for the basics of Spring Boot , maven, and git.

This project is part of a learning quest among two friends.
The two participants develope diferent projects but with similars tasks and on the same weekly schedule(ideally). 
The overall objective is the same for both of them.

# Table of Content
- [Project Objective](#project-objective)
- [Project Name](#flyingfeim-!!???)
- [Project Tasks](#project-tasks)
  - [1st Task](#1st-task)
  - [2nd Task](#2nd-task)
  - [3rd Task](#3rd-task)
  - [4th Task](#4th-task)
  - [5th Task](#5th-task)
  - [6th Task](#6th-task)
- [API Documentation](#api-documentation)
- [Afterthoughts based on random internet suggestion](#afterthoughts-based-on-random-internet-suggestion)

## Project Objective
The main objective of this project is to become familiar with some of the technologies used in software development and build confidence with the end goal of applying for junior software developer position.

## FlyingFeim !!???
FlyingFeim as said before is a learning project for spring boot, maven, and git.

The name is a combination of words derived from the participants names and inspired from the catchy nickname given to several quick Finnish athletes.
* Flying from translating the last name of one participant
* and Feim is part of the other participant's name


# Project Tasks
### 1st Task:
- Create a new spring boot project exposing a get endpoint that replies "Hello Word". Choose Java in language and make sure Maven is selected as the build automation tool. Lastly commit every change and push it to gitHub, don't forget to add the second participant as a contributor for both of you to have access to it.
### 2nd Task:
- Study some basic concepts of spring boot -spring beans, spring IOC(inversion of control), spring dependency injection - continue with the http protocol, and lastly learn about the REST API. Expand on the project by adding a service layer and a repository layer(Blank files).
  - Task 2.1: Study for 
    - DTOs, 
    - Mappers, 
    - Records, 
    - 3 tier architecture, 
    - Interfaces, 
    - Inheritance, 
    - Composition, 
    - == VS equals, 
    - Equals and hash code, 
    - Streams, 
    - Data structures (PX hashmaps), 
    - Spring annotations PX service, 
    - Component
    - Repository.
### 3rd Task:
- Make a VideoClub Managing System. Create a RESTful API with CRUD support for the most common actions to the database. Specs: list the available media for rent, list the rented media with any appropriate additional infoformation (e.g. who rented it and for how long, expacted return date, ect.), and a transaction system (e.g. rent confirmation ect.). For the transaction there can be 2 types of payment either pay upfront (overdue charges may apply on return) or charge by the day (i.e. 2 Euros per day if payed upfrond, 3 Euros/day on return, and 5 Euros per overdue pay). There sould be a confirmation message on success for both rent and return of the media(conrfirming both the correct payment and also the availability of the media) or else, in failure, the apropriate message should be displayed. Every successful action sould update the database accordingly (if an error message is displayed there souldn't be any changes in the db, unless maybe for logs).
(Preferable use PostgeSQL for the db).
 
### 4th Task:
- Unit Testing. Use Mockito and junit

### 5th Task:
- Transactional. Add transactional annotations and study about it.

### 6th Task:
- Dockerization. Create the appropriate "Dockerfile" and "docker-compose" files. Study about docker and containers. 


# API Documentation

This API provides endpoints to manage customer, movie, and rental data.

## API Table of Contents

- [Customer API Endpoints](#customer-api-endpoints)
  - [Register a Customer](#register-a-customer)
  - [Register a List of Customers](#register-a-list-of-customers)
  - [Update a Customer](#update-a-customer)
  - [Provide Feedback for a Customer](#provide-feedback-for-a-customer)
  - [Provide Feedback for a List of Customers](#provide-feedback-for-a-list-of-customers)
- [Movie API Endpoints](#movie-api-endpoints)
  - [Get Movie Information](#get-movie-information)
  - [Get List of Movies](#get-list-of-movies)
  - [Add a Movie](#add-a-movie)
  - [Add a List of Movies](#add-a-list-of-movies)
- [Rental API Endpoints](#rental-api-endpoints)
  - [Create a Rental](#create-a-rental)
  - [Return a Rental](#return-a-rental)
- [Data Transfer Objects](#data-transfer-objects)
  - [CustomerDTO](#customerdto)
  - [MovieDTO](#moviedto)
  - [RentalDTO](#rentaldto)

## Customer API Endpoints
Customer API Base URL:`/api/customer`

### Register a Customer

- **URL:** `/register`
- **Method:** `POST`
- **Request Body:**

  ```json
  {
    "username": "string",
    "email": "string",
    "dateOfBirth": "YYYY-MM-DD",  (optional)
    "isDeactivated": Boolean      (optional, default false)
  }
  ```


- Response:
  - Status: 200 OK
  - Body: String (Registration confirmation message)

### Register a List of Customers

- URL: `/list-register`
- Method: `POST`
- Request Body:

  ```json
  [
    {
      "username": "string",
      "email": "string",
      "dateOfBirth": "YYYY-MM-DD", (optional)
      "isDeactivated": Boolean (optional, default false)
    },
  ...
  ]
  ```

- Response:
  - Status: 200 OK
  - Body: String (Registration confirmation message)

### Update a Customer

- URL: `/update/{id}`
- Method: `PATCH`
- Path Variable: id (Customer ID)
- Request Body:

  ```json
  {
    "username": "string",
    "email": "string",
    "dateOfBirth": "YYYY-MM-DD",
    "isDeactivated": Boolean
  }
  ```

- Response:
  - Status: 200 OK
  - Body: String (Update confirmation message)

### Feedback for a Customer
(Feeds the request to the response without making any changes)
- URL: `/feedback`
- Method: `POST`
- Request Body:

  ```json
  {
    "customerId": Long,
    "username": "string",
    "email": "string",
    "dateOfBirth": "YYYY-MM-DD",
    "isDeactivated": Boolean
  }
  ```

- Response:
  - Status: 200 OK
  - Body: CustomerDTO (Customer data with feedback)

### Feedback for a List of Customers
(Feeds the request to the response without making any changes)
- URL: `/list-feedback`
- Method: `POST`
- Request Body:

  ```json
  [
    {
      "customerId": Long,
      "username": "string",
      "email": "string",
      "dateOfBirth": "YYYY-MM-DD",
      "isDeactivated": Boolean
    },
  ...
  ]
  ```

- Response:
  - Status: 200 OK
  - Body: List<CustomerDTO> (List of customer data with feedback)

## Movie API Endpoints
Customer API Base URL:`/api/movie`

### Get Movie Information

- URL: `/{id}`
- Method: `GET`
- Path Variable: id (Movie ID)

- Response:
  - Status: 200 OK
  - Body: MovieDTO (Movie information)
  
### Get List of Movies

- URL: `/list`
- Method: `GET`
- Query Parameters:
  - pageNumber (optional): Page number for pagination
  - pageSize (optional): Number of movies per page
- Response:
  - Status: 200 OK
  - Body: List<MovieDTO> (List of movie information)
    
### Add a Movie

- URL: `/add`
- Method: `POST`
- Request Body:
  ```json
    {
      "movieId": Long,
      "title": "string",
      "releaseYear": "YYYY",
      "availableStock": Integer,
      "totalStock": Integer
    }
  ```

- Response:
  - Status: 200 OK
  - Body: String (Addition confirmation message)
    
### Add a List of Movies

- URL: `/list-add`
- Method: `POST`
- Request Body:

  ```json
  [
    {
      "movieId": Long,
      "title": "string",
      "releaseYear": "YYYY",
      "availableStock": Integer,
      "totalStock": Integer
    },
  ...
  ]
  ```

- Response:
  - Status: 200 OK
  - Body: String (Addition confirmation message)
    
## Rental API Endpoints
Customer API Base URL:`/api/rental`

### Create a Rental
- URL: `/new`
- Method: `POST`
- Request Body:

  ```json
  {
    "movieId": Long,
    "customerId": Long,
    "rentalDate": "YYYY-MM-DD"  (optional)
  }
  ```

- Response:
  - Status: 200 OK
  - Body: RentalDTO (Rental information)
  
### Return a Rental

- URL: `/return`
- Method: `PATCH`
- Request Body:
  ```json
  {
    "rentalId": Long,
    "amountPaid": Double
  }
  ```

- Response:
  - Status: 200 OK
  - Body: RentalDTO (Updated rental information)

## Data Transfer Objects
- CustomerDTOThe CustomerDTO class represents the data transfer object for customer information.

  ```java
  public class CustomerDTO {
      private Long customerId;
      private String username;
      private String email;
      private LocalDate dateOfBirth;
      private Boolean isDeactivated;
  }
  ```

- MovieDTOThe MovieDTO class represents the data transfer object for movie information.

  ```java
  public class MovieDTO {
      private Long movieId;
      private String title;
      private Year releaseYear;
      private Integer availableStock;
      private Integer totalStock;
  }
  ```

- RentalDTOThe RentalDTO class represents the data transfer object for rental information.

  ```java
  public class RentalDTO {
      private Long rentalId;
      private Long movieId;
      private Long customerId;
      private LocalDate rentalDate;
      private LocalDate returnDate;
      private Double rentalCost;
      private Double amountPaid;
      private Double changeDue;
  }
  ```


# Afterthoughts based on random internet suggestion.
- Segment the logic to more than one microservices. Deploy them to docker, and create a docker container of apache or nginx, or any other, which will be proxying the requests based on context path to correct web app.
