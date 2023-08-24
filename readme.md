# Spring Boot JWT Backend
A Spring Boot backend application that utilizes JWT (JSON Web Tokens) for authentication and authorization.

## Overview
This project provides a robust backend setup with JWT authentication. Users can register, authenticate, and make API calls secured with JWT. All of the users are saved on a local postgresql server.  

![403](gh-images/diagram.png)

### An API call to **localhost:8080/api/v1/demo** without JWT token:
![403](gh-images/403.png)

### An API call to **localhost:8080/api/v1/demo** with JWT token in the header:
![200](gh-images/200.png)
```
Authorization: bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrcmluc0BlbWFpbC5jb20iLCJpYXQiOjE2OTI4OTY5OTMsImV4cCI6MTY5MzUwMTc5M30.Vuqr89siFnWeB3p4AO-epUtpODZ-PcCLjwlcqNOJ4Nw
``````

### To create a new account: Send a POST request to **localhost:8080/api/v1/register** with these in the body, and that will create an account and response with a token
```json
{
  "firstname": "krins",
  "lastname": "krins",
  "email": "krins@email.com",
  "password": "password"
} 
``` 
![Token](gh-images/token.png)
![postgres](gh-images/db.png)

### if you are already registered and you need a new token, you can make a POST request to **localhost:8080/api/v1/authenticate** with email and password in the body, and that should return with a new JWT token.
![Token](gh-images/token.png)

### The payload of the jwt contains information about the email, expiration date and issue date
```json
{
  "sub": "krins@email.com",
  "iat": 1692896993,
  "exp": 1693501793
}
``````