## Springboot-jwt-demo

##About
Springboot-jwt-demo is a demonstration of a completely stateless and RESTful token-based authorization system using JSON Web Tokens (JWT) and Springboot.

##Requirements
Requires Maven and Java 1.7 or greater.

##Usage
To use start Springboot-jwt-demo, run in the terminal `mvn spring-boot:run`. Springboot-jwt-demo will now be running at `http://localhost:8080`

There are two built-in user accounts to demonstrate the differing levels of access to the endpoints:
```
User - user:123
Admin - admin:123
```

JWT Tokens are configured to expire after 10 minutes, you can get a new token by sign in again. They are saved in a http-only cookie instead of localstorage thi will prevent csrf attacks

The frontend code is based on [Spring Security and Angular JS](https://spring.io/guides/tutorials/spring-security-and-angular-js/)

[Stormpath article](https://stormpath.com/blog/token-auth-spa)

The project is heavyly inspired by [Cerberus](https://github.com/brahalla/Cerberus).
