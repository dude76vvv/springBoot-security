# Spring Boot Security
The aim of this project is to implement REST API endpoints using springboot framework.<br/>
To protect the endpoints, Users will need to login to retrieve JSON Web Tokens (JWT).
Only with these tokens, users can then access the endpoints. <br/><br/>

To allow better access control of endpoints, users are also assign USER or ADMIN role. <br/> 
Important endpoints can only be reachable by users who are ADMIN  role. <br/><br/>

JWT are stateless in nature. In effort to be able to invalidate JWT, a table is used to keep track of the JWT generated. <br/>
JWT can be revoked by setting a boolean flag in table.

## Features
* User Registeration
* JWT authentication
* Role-based authorization
* Revoke JWT

## Technologies used
* Spring Boot 
* Spring Security 6.1
* JSON Web Tokens (JWT)
* BCrypt
* Maven
* MySQL
